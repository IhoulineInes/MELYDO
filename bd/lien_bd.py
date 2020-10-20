from sqlalchemy import create_engine
import pandas as pd
import os

def create_session():

     create_session = "mysql+pymysql://root:@localhost/melydo"
     connexion = create_engine(create_session)

     return connexion

#------------------------- USER --------------------------------
def connexion_user(login, mdp):

    connexion = create_session()
    requete = "SELECT id_user, login, mail, pref, mdp, date FROM user WHERE login = '%s' AND mdp = '%s'" % (login, mdp)
    print(requete)
    user = pd.read_sql(requete, con=connexion, index_col=None)
    return user


def inscription_user(login, mdp, mail, pref):
    try:
        connexion = create_session()
        requete = {"login":login,"mail":mail,"pref":pref,"mdp":mdp}
        new = pd.DataFrame(requete,index=[None])
        new.to_sql('user', connexion, index=False, if_exists="append")
        return "OK"
    except Exception as e :
        return "KO"


def modification_param( mdp, pref,id):
    try:
        mdp = "'"+mdp+"'"
        pref = "'"+pref+"'"
        connexion = create_session()
        requete = "UPDATE `user` SET `mdp` = %s, `pref` = %s WHERE `id_user` = %s" %(mdp,pref,id)
        print(requete)
        modif = pd.read_sql(requete, con=connexion, index_col=None)
        return ('OK')
    except Exception as e:
        return "KO"

def getPref(id_user):

    connexion = create_session()
    requete = "SELECT pref FROM user WHERE id_user = %s " % (id_user)
    print(requete)
    pref = pd.read_sql(requete, con=connexion, index_col=None)
    return pref['pref'][0]


#--------------------------------Likes----------------------

def likes(id):
    connexion = create_session()
    requete = "SELECT distinct path from `likes` l inner join `musique` m on l.id_musique=m.id  WHERE id_user=%s " %id
    likes = pd.read_sql(requete, con=connexion,index_col=None)
    return likes


def dontLike(id_user, id_musique):
    print(id_user)
    print(id_musique)
    try:
        connexion = create_session()
        print(">>")
        requete = "DELETE FROM `likes` WHERE id_user=%s and id_musique=%s " %(id_user,id_musique)

        pd.read_sql(requete, con=connexion,index_col=None)

        return "OK"
    except Exception as e:
        return "KO"

def add_like(idd, id_musique):
    try:
        connexion = create_session()
        requete = {"id_musique":id_musique,"id_user":idd}

        new = pd.DataFrame(requete,index=[None])

        new.to_sql('likes', connexion, index=False, if_exists="append")
        return "OK"
    except Exception as e :
        return "KO"

def getNbLike():
    print("getNbLike")
    connexion = create_session()
    requete = "Select id_user, nb_ecoute FROM ecoutes "

    nb_like = pd.read_sql(requete, con=connexion, index_col=None)

    return ()
#--------------------------MUSIQUE----------------------
def getPath(titre):
    connexion = create_session()
    requete = "SELECT path from `musique` WHERE nom_musique=%s " %titre
    path = pd.read_sql(requete, con=connexion,index_col=None)
    return path


def getIdMusique_titre(titre):
    titre = "'"+titre+"'"
    connexion = create_session()
    requete = "SELECT id from `musique` WHERE nom_musique=%s " %titre

    id = pd.read_sql(requete, con=connexion,index_col=None)

    return id['id'][0]

def getIdMusique_path(path):
    connexion = create_session()
    path = "'"+path+"'"
    requete = "SELECT id from `musique` WHERE path=%s " %path
    print(requete)
    id = pd.read_sql(requete, con=connexion,index_col=None)
    return id['id'][0]

def getPath(id_musique):
    connexion = create_session()

    requete = "SELECT path from `musique` WHERE id=%s " %id_musique
    print(requete)
    id = pd.read_sql(requete, con=connexion)
    return id['path'][0]

def liste_genre():
    __PATH__ = str(os.getcwd()).replace('\\', '/') + '/static/sound/'

    return os.listdir(__PATH__)



def liste_musique():

    __PATH__ = str(os.getcwd()).replace('\\', '/')+'/static/sound/'
    dict_sound_par_genre = {}

    for dossier in os.listdir(__PATH__):
        liste_musique = []
        print(dossier)
        open_dossier = os.listdir(__PATH__+dossier)
        for sound in open_dossier:
            liste_musique.append("sound/"+dossier+'/'+sound)
        dict_sound_par_genre[dossier] = liste_musique

    return dict_sound_par_genre

def liste_genre_musique(genre):

    __PATH__ = str(os.getcwd()).replace('\\', '/')+'/static/sound/'

    liste_musique = []
    open_dossier = os.listdir(__PATH__+genre)
    for sound in open_dossier:
        liste_musique.append("sound/"+genre+'/'+sound)

    return liste_musique

###########
def musique_by_id(id_musique):

    connexion = create_session()
    requete = "SELECT path date FROM musique WHERE id = '%s' " % (id_musique)
    path = pd.read_sql(requete, con=connexion, index_col=None)

    return path
############

#---------------------------ECOUTE------------------
def get_nb_ecoute(idd,id_musique):
    print("get_nb_ecoute")
    connexion = create_session()
    requete = "Select nb_ecoute FROM ecoutes where `id_musique`=%s and `id_user` =%s "%(id_musique,idd)

    nb_ecoute = pd.read_sql(requete,con=connexion, index_col=None)

    return (int(nb_ecoute['nb_ecoute'][0]))

def nb_ecoute(idd):
    print("get_nb_ecoute")
    connexion = create_session()
    requete = "Select nb_ecoute FROM ecoutes where id_user=%s " % idd
    nb_ecoute = pd.read_sql(requete, con=connexion, index_col=None)
    return (int(nb_ecoute['nb_ecoute'][0]))


def ecouteExist(path,idd):
    connexion = create_session()
    path = "'"+path+"'"
    requete = "SELECT count(*) FROM ecoutes e INNER JOIN musique m ON m.id = e.id_musique where m.path = %s and e.id_user=%s" %(path,idd)
    id = pd.read_sql(requete, con=connexion, index_col=None)
    if id.empty:
        return False

    return id['count(*)'][0]


def listIdMusique_ecoute(idd):
    connexion = create_session()
    requete = "SELECT `id_musique` FROM `ecoutes` WHERE `id_user`=%s" %(idd)
    id = pd.read_sql(requete, con=connexion, index_col=None)
    if id.empty:
        return []

    return id


def add_new_ecoute(idd, id_musique):
    print("add_new_ecoute")

    try:

        connexion = create_session()
        requete = "INSERT INTO `ecoutes`(`id_musique`, `id_user`) VALUES (%s,%s)" % (id_musique, idd)

        add = pd.read_sql(requete, con=connexion, index_col=None)


        return "OK"
    except Exception as e :
        return "KO"

def add_ecoute(idd, id_musique):
    print("add_ecoute")
    connexion = create_session()
    nb = get_nb_ecoute(idd, id_musique)+1

    requete = "UPDATE `ecoutes` SET `nb_ecoute`=%s where `id_musique`=%s and `id_user`=%s" %(nb,id_musique,idd)

    nb_ecoute = pd.read_sql(requete, con=connexion, index_col=None)




#-------------------------------TELECHARGEMENT------------------------
def add_dow(idd, id_musique):
    try:
        connexion = create_session()
        requete = {"id_musique":id_musique,"id_user":idd}
        print(requete)
        new = pd.DataFrame(requete,index=[None])
        print(new)
        new.to_sql('downloads', connexion, index=False, if_exists="append")
        return "OK"
    except Exception as e :
        return "KO"

def getNbDw():
    print("getNbLike")
    connexion = create_session()
    requete = "Select id_user, nb_ecoute FROM downloads "

    nb_like = pd.read_sql(requete, con=connexion, index_col=None)

    return ()

#--------------------------------PROPOSITION--------------------------------------------

def users_songs(id):
    print("users_songs")
    id=int(id)
    connexion = create_session()
    requete = "select `id_musique`, `id_user`, `nb_ecoute` FROM `ecoutes` WHERE `id_user` = %s"%id

    res = pd.read_sql(requete, con=connexion, index_col=None)

    return (res)




def all_user_songs(id):
    print("users_songs")
    connexion = create_session()
    requete = "SELECT `id_musique`, `id_user`, `nb_ecoute` FROM `ecoutes` WHERE `id_musique` in (SELECT id_musique from ecoutes where id_user = % s)"%id

    #requete = "select `id_musique`,`id_user` FROM `ecoutes` WHERE `id_user` = %s union all SELECT `id_musique`, `id_user` FROM `downloads` WHERE `id_musique` in (SELECT `id_musique` FROM `ecoutes` WHERE `id_user` = %s) union all SELECT `id_musique`,`id_user` FROM `likes` WHERE `id_musique` in (SELECT `id_musique` FROM `ecoutes` WHERE `id_user` = %s)"%(id,id,id)


    res = pd.read_sql(requete, con=connexion, index_col=None)

    return (res)
