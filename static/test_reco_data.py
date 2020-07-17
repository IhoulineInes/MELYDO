from random import randrange

nb_users = 1000
nb_reco_per_user = 20


users_reco = []

for i in range(nb_users):
	users_reco.append([])
	for j in range(nb_reco_per_user):
		users_reco[i].append(randrange(1000) + 1)

print(users_reco[0])