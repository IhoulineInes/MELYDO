###
#   Script de recommandation adapté au site web Melydo
#   Script basé sur les utilisateurs
###



## test
# import pandas as pd
# from random import randrange
from bd import lien_bd

def proposition(id):

    ### Script de recommandation basé sur les utilisateurs ###
    # No like no DL version
    # Goal : find the closest users

    # s: [id_musique, note, nb_ecoutes, like, download]
    #note = int(s[2] / ((s[2]*0.15) + 0.8)) + (4*s[3]) + (3*s[4])
    def note_user_song(s):
        note = int((s[2]/3)+0.5)
        if note > 10:
            note = 10
        return note



    ### DATA GENERATION

    ##tests
    all_user_songs = lien_bd.all_user_songs(id)
    # data = []
    # for i in range(2000):
    #     data.append([randrange(1,100), randrange(1,100), randrange(1,20)])
    # all_user_songs = pd.DataFrame(data, columns=['id_musique', 'id_user', 'nb_ecoutes'])
    # print(all_user_songs)

    user_songs_df = lien_bd.users_songs(id)
    # data = []
    # for i in range(20):
    #     data.append([randrange(1,100), randrange(1,100), randrange(1,20)])
    # user_songs_df = pd.DataFrame(data, columns=['id_musique','id_user', 'nb_ecoutes'])
    # print(user_songs_df)




    ### DATA CONVERSION

    users_songs = [[] for i in range(5100)]

    for index, row in all_user_songs.iterrows():
        users_songs[row['id_user']].append([row['id_musique'], int((row['nb_ecoute']/3)+0.5), row['nb_ecoute']])

    #print('\nusers_songs :\n',users_songs[1])



    user_songs = []

    for index, row in user_songs_df.iterrows():
        user_songs.append([row['id_musique'], int((row['nb_ecoute']/3)+0.5), row['nb_ecoute']])

    #print('\nuser_songs :\n',user_songs)





    user_songs_ids = [s[0] for s in user_songs]
    #print('\nuser_songs_ids :\n',user_songs_ids[:20])

    users_scores = []
    id_user = -1
    for u in users_songs:
        id_user += 1
        user_score = 0
        for s in u:
            for su in user_songs:
                if (s[0] == su[0]):
                    user_score += note_user_song(s) * note_user_song(su)
        users_scores.append([id_user, (user_score / (len(u) + 1))])

    users_scores = sorted(users_scores, key=lambda x: x[1], reverse=True)
    users_scores = users_scores[:40]
    #print('\nusers_scores :\n',users_scores)


    # Check in all the most similar users the musics that appear the most often.

    frequent_songs = {}
    for u in users_scores:
        id_user = u[0]
        score_user = u[1]
        for s in users_songs[id_user]:
            id_music = s[0]
            if (id_music not in user_songs_ids):
                if (id_music in frequent_songs): 
                    frequent_songs[id_music] += score_user
                else: 
                    frequent_songs[id_music] = score_user

    #print(frequent_songs)
    frequent_songs = {k: v for k, v in sorted(frequent_songs.items(), key=lambda item: item[1], reverse=True)}
    user_based_reco = list(frequent_songs.keys())[:20]


    #print('\nuser_based_reco :\n',user_based_reco)
    return (user_based_reco)

#proposition(123)


