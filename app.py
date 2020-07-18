from flask import Flask, render_template, request, url_for, redirect, session, flash
from bd import lien_bd
import reco_data
import reco_proposition
import os
from functools import wraps
from werkzeug.utils import secure_filename


UPLOAD_FOLDER = os.getcwd()+'/static/sound/'
ALLOWED_EXTENSIONS = {'wav', 'mp3', 'raw', 'bwf', 'caf', 'aiff'}



app = Flask(__name__)
app.secret_key = os.urandom(24)


def allowed_file(filename):
    return '.' in filename and \
           filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS



#------------------ Connexion /Deconnexion ---------------

@app.route('/')
def enregistrement():
    # lien_bd.connexion_user("ness", "1234")
    return render_template('enregistrement.html')



@app.route('/login', methods=['GET', 'POST'])
def ouvrirsession():
    if request.method == "POST":

        login = request.form['login']
        mdp = request.form['mdp']

        result = lien_bd.connexion_user(login, mdp)
        if result.empty:
            return render_template('ouvrirsession.html', erreur_connexion=True)

        session['est_co'] = True
        session['login'] = str(result.login[0])
        session['id'] = str(result.id_user[0])
        session['mail'] = str(result.mail[0])
        session['date'] = str(result.date[0])
        session['pref'] = str(result.pref[0])
        session['mdp'] = str(result.mdp[0])

        return redirect(url_for('session_new'))

    return render_template('ouvrirsession.html')


def is_logged_in(f):
    @wraps(f)
    def wrap(*args, **kwargs):
        if 'est_co' in session:
            return f(*args, **kwargs)
        else:
            flash('unauthorised,please login', 'danger')
            return redirect(url_for('ouvrirsession'))

    return wrap



@app.route('/logout', methods=['GET', 'POST'])
@is_logged_in
def logout():
    session.clear()
    return redirect(url_for('ouvrirsession'))




@app.route('/inscription/new/user', methods=['GET', 'POST'])
def new_inscription():
    if request.method == "POST":
        login = request.form['login']
        mail = request.form['email']
        pref = request.form.getlist('pref[]')
        mdp = request.form['mdp']
        mdpbis = request.form['mdpbis']

        print(login, mail, pref, mdp, mdpbis)

        if (mdpbis != mdp):
            return render_template('enregistrement.html', erreur_mdp=True)

        result_inscription = lien_bd.inscription_user(login, mdp, mail, '-'.join(item for item in pref))

        return render_template('ouvrirsession.html', compte_cree=True)




@app.route('/modification', methods=['GET', 'POST'])
def modification_param():
    if request.method == "POST":
        pref = request.form.getlist('pref[]')
        mdp = request.form['mdp']
        mdpbis = request.form['mdpbis']

        print(pref, mdp, mdpbis)

        if (mdpbis != mdp):
            return render_template('monCompte.html', erreur_mdp=True)

        result_inscription = lien_bd.modification_param(mdp, '-'.join(item for item in pref),session.get('id'))

        return render_template('monCompte.html', compte_modifier=True)


@app.route('/parametre', methods=['GET', 'POST'])
def monCompte():
    idd = session.get("id")
    return render_template('monCompte.html', id = idd)


@app.route('/parametre/modification', methods=['GET', 'POST'])
def modificationCompte():
    return render_template('modificationCompte.html', modification=True)



#---------------------------MUSIQUE------------------------


@app.route('/musique', methods=['GET', 'POST'])
def session_new():
    print(session)
    musiques = lien_bd.liste_musique()
    return render_template('liste_musique.html', musiques=musiques)


@app.route('/musique/add_musique', methods=['GET', 'POST'])
def add_musique():
    UPLOAD_FOLDER = os.getcwd() + '/static/sound/'
    if request.method=='POST':
        UPLOAD_FOLDER=UPLOAD_FOLDER+request.form["dossier"]
        app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
        file = request.files['filename']
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            return "Fichier charger"

    return render_template('add_musique.html')



#----------------------------------LIKE---------------------

@app.route('/musique/mesLikes', methods=['GET', 'POST'])
def mesLikes():
    idd = session.get("id")
    mesLikes = lien_bd.likes(idd)
    for m in range(mesLikes['path'].size):
        titre = mesLikes['path'][m].split("/")[4].split(".wav")[0]

    return render_template('liste_like.html', musiques=mesLikes['path'])

@app.route('/musique/dont_like/<string:titre>', methods=['GET', 'POST'])
def dontLike(titre):
    idd = session.get("id")
    id_musique=lien_bd.getIdMusique_titre(titre)
    print("id= "+idd)
    print(id_musique)
    dontLikes = lien_bd.dontLike(idd,id_musique)
    print(dontLikes)
    if dontLikes == "KO":
        return redirect(url_for('mesLikes'))
    else:
        return redirect(url_for('erreur'))


@app.route('/musique/like/<string:genre>/<string:titre>',methods=['GET','POST'])
def ajouter_like(genre,titre):
    idd = session.get('id')
    print(titre)
    path = "/static/sound/"+ genre + "/" + titre + ".wav"
    print(path)
    id_musique = lien_bd.getIdMusique_path(path)
    add_like = lien_bd.add_like(idd, id_musique)
    if(add_like == 'OK'):
        return redirect(url_for('mesLikes'))
    else:
        return redirect(url_for('erreur'))


#--------------------------------------TELECHAREMENT-------


@app.route('/musique/downloads',methods=['GET','POST'])
def ajouter_telechargement():
    idd = session.get('id')
    path = request.form['url_sound']
    print("URL>>>>>>>>>>>>>>>>>>>")
    print(path)
    id_musique = lien_bd.getIdMusique_path(path)
    print(id_musique)
    add_like = lien_bd.add_dow(idd,id_musique)
    if(add_like == 'OK'):
        return redirect(url_for('session_new'))
    else:
        return redirect(url_for('erreur'))




@app.route('/telechargement', methods=['GET', 'POST'])
def telechargement():
        return render_template('telechargement.html')




#-------------------------------------------ECOUTE----------

@app.route('/musique/add_ecoute', methods=['POST'])
def add_ecoute():
    idd = int(session.get('id'))
    path = request.form["musique_path"]
    print("APP")
    print(path)
    path='/static/'+path
    id_musique = lien_bd.getIdMusique_path(path)
    if (not lien_bd.ecouteExist(path,idd)):
        print("il y a pas d'ecoute")
        lien_bd.add_new_ecoute(idd, id_musique)
    else :
        print("on a deja une ecoute")
        lien_bd.add_ecoute(idd,id_musique)

    return("je suis la")


#------------------------------PROPOSITION-------------


@app.route('/musique/propositions', methods=['GET','POST'])
def propositions():
    idd = int(session.get('id'))
    liste_path =[]
    print(lien_bd.nb_ecoute(idd))
    if(lien_bd.nb_ecoute(idd)<10):
        song = reco_data.liste_musique(idd)
    else:
        song = reco_proposition.proposition(idd)
    for i in song:
        liste_path.append(lien_bd.getPath(i))
    return render_template('proposition.html', musiques=liste_path)


@app.route('/musique/propositions/downloads',methods=['GET','POST'])
def ajouter_telechargement_proposition():
    idd = session.get('id')
    path = request.form['url_sound']
    print("URL>>>>>>>>>>>>>>>>>>>")
    print(path)
    id_musique = lien_bd.getIdMusique_path(path)
    print(id_musique)
    add_like = lien_bd.add_dow(idd,id_musique)
    if(add_like == 'OK'):
        return redirect(url_for('propositions'))
    else:
        return redirect(url_for('erreur'))

#-----------------------------ERREUR ---------------------

@app.route('/erreur', methods=['GET', 'POST'])
def erreur():
        return render_template('erreur.html')




if __name__ == '__main__':
    app.run(debug=True, host="127.0.0.1", port=9800)

