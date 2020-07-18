from random import randrange
from bd import lien_bd

n=0
all_user_songs=0


def preference(id_user):
	pref=lien_bd.getPref(id_user)
	pref=str(pref)
	l=pref.split("-")
	return l
#preference(34)


def list_user(id_user):
	nb_reco_per_user = 20
	users_reco = []
	pref = preference(id_user)
	print("Les pref sont :")
	n = list()
	print("ok")
	print(n)
	for i in pref:
		if i == "Blues":
			n.append(1)
		if i == "Classical":
			n.append(100)
		if i == "Country":
			n.append(200)
		if i == "Disco":
			n.append(300)
		if i == "Hiphop":
			n.append(400)
		if i == "Jazz":
			n.append(500)
		if i == "Metal":
			n.append(600)
		if i == "Pop":
			n.append(700)
		if i == "Reggae":
			n.append(800)
		if i == "Rock":
			n.append(900)
	print(n)
	list_ecoute = lien_bd.listIdMusique_ecoute(id_user)
	#list_ecoute=list_ecoute.to_numpy()
	print(list_ecoute)

	for j in range(int(nb_reco_per_user/len(n))):
		for i in range(len(n)):
			print("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
			print(n[i])
			r = randrange(100) + n[i]
			while r in list_ecoute.keys() :
				r = randrange(100) + n[i]
			users_reco.append(r)
	return users_reco


def liste_musique(id):
	user_reco = list_user(id)
	print(user_reco)
	return user_reco
#liste_musique(34)


def note_user_song(s):
    note = int(s[2] / ((s[2]*0.15) + 0.8)) + (4*s[3]) + (3*s[4])
    if note > 10:
        note = 10
    return note


def proposition(id):
	all_user_songs = lien_bd.all_user_songs(id)
	print(all_user_songs)

	user_songs = lien_bd.users_songs(id)
	print(user_songs)
	user_songs_ids = list(user_songs['id_musique'])


	users_scores = {}


	for index, row in all_user_songs.iterrows():
		row['id_user']
		user_score = 0
		row['id_musique']
		for index2,row2 in user_songs.iterrows():
			user_score+=1

		if (row['id_user'] in users_scores):
			users_scores[row['id_user']] += 1
		else:
			users_scores[row['id_user']] = 1

	#print(users_scores)
	users_scores_d = {k: v for k, v in sorted(users_scores.items(), key=lambda item: item[1], reverse=True)}
	#print(users_scores_d)
	users_scores=[]


	for key, value in users_scores_d.items():
		users_scores.append([key,value])
	#print("users_scores")
	#print(users_scores)
	dic_musique_user = {}


	users_songs =[ [] for i in range(5100)]
	print(users_songs)

	for index, row in all_user_songs.iterrows():
		users_songs[row['id_user']].append(row['id_musique'])

	print(users_songs)
	frequent_songs={}
	for u in users_scores:
		score_user = u[1]
		for id_music in users_songs[u[0]]:
			print(id_music)
			if (id_music not in user_songs_ids):
				if (id_music in frequent_songs):
					frequent_songs[id_music] += score_user
				else:
					frequent_songs[id_music] = score_user

	frequent_songs = {k: v for k, v in sorted(frequent_songs.items(), key=lambda item: item[1], reverse=True)}
	user_based_reco = list(frequent_songs.keys())[:20]
	print (user_based_reco)
	return (user_based_reco)

proposition(5001)

'''

	for u in all_user_songs['id_user']:
		
		for s in u:
			for su in user_songs:
				if (s[0] == su[0]):
					user_score += note_user_song(s) * note_user_song(su)
		users_scores.append([id_user, (user_score / (len(u) + 1))])

	users_scores = sorted(users_scores, key=lambda x: x[1], reverse=True)
	users_scores = users_scores[:40]
	print(users_scores)

	# Check in all the most similar users the musics that appear the most often.

	frequent_songs = {}
	for u in users_scores:
		id_user = u[0]
		score_user = u[1]
		for s in all_user_songs['id_user']:
			id_music = s[0]
			if (id_music not in user_songs_ids):
				if (id_music in frequent_songs):
					frequent_songs[id_music] += score_user
				else:
					frequent_songs[id_music] = score_user

	frequent_songs = {k: v for k, v in sorted(frequent_songs.items(), key=lambda item: item[1], reverse=True)}
	user_based_reco = list(frequent_songs.keys())[:20]

	return (user_based_reco)
'''

print(proposition(224))

