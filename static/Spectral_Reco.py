#!/usr/bin/env python
# coding: utf-8

# In[1]:


#import librosa
import pandas as pd
import numpy as np
from scipy.spatial import distance
from IPython import get_ipython
#import matplotlib.pyplot as plt
get_ipython().run_line_magic('matplotlib', 'inline')
# import os
#from PIL import Image
#import pathlib
#import csv
import operator
import IPython.display as ipd

#from sklearn.model_selection import train_test_split
#from sklearn.preprocessing import LabelEncoder, StandardScaler

from random import randrange, expovariate


#import tensorflow.keras


# In[2]:


path = './genres/'
genres = 'blues classical country disco hiphop jazz metal pop reggae rock'.split()


# In[4]:


songs_data = pd.read_csv('data.csv')

# filename and label columns are not necessary in the dataset (label can be found with the index on the song)
songs_files = songs_data.values[:,0]
songs_spectra = songs_data.drop(['filename', 'chroma_stft', 'rmse', 'spectral_centroid', 'spectral_bandwidth', 'rolloff', 'zero_crossing_rate', 'label'],axis=1).values

print(songs_data.shape)
print(songs_data.head())
print(songs_spectra.shape)


# In[5]:


### Initiate variables ###

nb_users = 5000
nb_songs = 1000
nb_songs_per_genre = 100
nb_genres = 10
max_listened_musics_per_users = 1000

nb_reco_per_user = 20


# In[6]:


#### GENERATE USER DATA ####

# users_songs : [[[id_song, note]]] => note 1..10

users_genre = []
users_genre_part = []
users_nb_songs = []
users_songs = []

for i in range (nb_users):
    user_nb_songs = int(expovariate(0.01))
    if user_nb_songs > max_listened_musics_per_users:
        user_nb_songs = max_listened_musics_per_users
    users_nb_songs.append(user_nb_songs)
    
    user_genre = randrange(10)
    users_genre.append(user_genre)
    
    user_genre_part = randrange(7)+2 # 2 .. 8
    users_genre_part.append(user_genre_part)
    
    user_songs = []
    
    for j in range(user_nb_songs):
        note = int(expovariate(0.1)) + 1
        while note > 10:
            note = int(expovariate(0.1)) + 1
        if (randrange(10) < user_genre_part): # take music from specific genre
            user_songs.append([randrange(100)+(user_genre*100), note])
        else:
            user_songs.append([randrange(1000), note])
    
    users_songs.append(user_songs)

#np_users_songs = np.asarray(users_songs,dtype=int) => ERROR sequence to np array

print(users_genre[0])
print(users_songs[0])


# In[7]:


# Make a spectrum for each user, from each of his musics listened (depending to his rate)
users_spectra = []
for user in users_songs:
    user_spectra = []
    for song in user:
        for i in range(song[1]):
            user_spectra.append(songs_spectra[song[0]])
    np_user_spectra = np.array(user_spectra, dtype=float)
    #user_spectrum = np.mean(np_user_spectra, axis=0)
    users_spectra.append(np.mean(np_user_spectra, axis=0)) #user_spectrum

print(users_spectra[0])


# In[8]:


# print users_mean_spectra errors
i=0
for s in users_spectra:
    print(i)
    if not isinstance(s,np.ndarray):
        print("!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!")
        print('>>>> spectre :',i,' - ',s)
        print("!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!")
    else:
        j=0
        for f in s:
            if not isinstance(f, float):
                print("!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!")
                print('>>>>',j,'<<<<')
                print("!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!!!!")
            j+=1
    i+=1


# In[9]:


def find_closest_musics_not_listened(user_spectrum, musics_listened, nb_recos=nb_reco_per_user):
    distances = {}
    i=0
    for song_spectrum in songs_spectra:
        distances[i] = distance.euclidean(user_spectrum, song_spectrum)
        i+=1
    
    distances = sorted(distances.items(), key=operator.itemgetter(1))
    
    ir=0
    recos=[]
    for j in range(nb_recos):
        while distances[ir][0] in musics_listened:
            ir+=1
        recos.append(distances[ir][0])
        ir+=1
        
    return recos


# In[10]:


### Finally, let's execute the reco loop
users_reco = []
i=0
for user in users_songs:
    if not isinstance(users_spectra[i],np.ndarray):
        users_reco.append([])
    else:
        user_songs = []
        for s in user:
            user_songs.append(s[0])
        users_reco.append(find_closest_musics_not_listened(users_spectra[i], user_songs))
    i+=1
print(users_reco[:5])


# In[ ]:





# In[11]:


def print_user(id_user):
    print(genres[users_genre[id_user]])
    print(users_genre_part[id_user])
    #print(users_songs[id_user])
    print(users_reco[id_user])


# In[12]:


def create_player(id_music):
    audio_info = songs_data.loc[id_music]
    audio_path = path+audio_info['label']+'/'+audio_info['filename']
    return ipd.Audio(audio_path)


# In[ ]:





# In[82]:


### VISUALISATION ###
## Les différents tableaux utiles pour l'aprentissage :
## users_songs : [[id_song1, note_user_song1], [id_song2, note_user_song2], ...]
print(users_songs[0],'\n')
## users_spectra (spectra = spectre au pluriel ;) : [ spectre user 1, spectre user 2, ... ] => [[-51.55399323  86.15279388 -20.2741909 59.04638672 ...], ...]
print(user_spectra[0],'\n')
## songs_spectra : [ spectre song 1, spectre song 2, ... ] => [[-51.55399323  86.15279388 -20.2741909 59.04638672 ...], ...]
print(songs_spectra[0],'\n')


# In[ ]:





# In[ ]:


### CREATION DU MODELE ###

# 
#
#  
#
#


# In[ ]:





# In[ ]:





# In[64]:


### TEST RECO ###
# Choose a user id by changing this value :
user_test = 7
# and run the cell
print_user(user_test)
cpt_reco = -1


# In[65]:


# Checks music recommandation for the user_test (check the next music by incrementing the cell - no loop possible with the audio render)
cpt_reco+=1
create_player(users_reco[user_test][cpt_reco])


# In[ ]:





# In[ ]:





# In[ ]:


#file = open('users.csv', 'a', newline='')
#with file:
#    writer = csv.writer(file)
#    writer.writerow(to_append.split())
#
#file = open('reco.csv', 'a', newline='')
#with file:
#    writer = csv.writer(file)
#    writer.writerow(to_append.split())


# In[ ]:





# In[ ]:





# In[ ]:




