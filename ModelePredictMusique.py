import copy
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
import numpy as np

# from tensorflow.keras import Sequential
# from tensorflow.keras.layers import Dense 

def get_max_len(d : dict):
    values = d.values()
    max_len = 0

    for i in values:
        if(max_len < len(i)):
            max_len = len(i)

    return max_len

def init_data_dict(data_dict : dict, d : dict, last_count : int):
    max_len = get_max_len(d)

    for key in d.keys():
        if(key not in data_dict.keys()):
            data_dict[key] = [0 for i in range(last_count)]

        value = d[key]
        value_len = len(value)
        for i in range(max_len):
            if(i < value_len):
                data_dict[key].append(value[i])
            else:
                data_dict[key].append(0)

    return

# def create_model():
#     model = Sequential()
#     model.add(Dense(533, activation = 'relu'))
#     model.add(Dense(1, activation = 'relu'))


#     model.compile(optimizer='sgd', loss='mse')
#     return model
        

if __name__ == "__main__":
    
    downloads = open("C:/Users/JULES LAMA/Documents/Dossier PA/data_bdd_melydo/downloads.csv", "r")
    ecouter = open("C:/Users/JULES LAMA/Documents/Dossier PA/data_bdd_melydo/ecoutes.csv", "r")
    likes = open("C:/Users/JULES LAMA/Documents/Dossier PA/data_bdd_melydo/likes.csv", "r")

    data_downloads = dict()
    data_likes = dict()
    data_ecoutes = dict()
    downloads.readline()
    ecouter.readline()
    likes.readline()

    for line in downloads.readlines():
        line = line.split(",")
        user_id = int(line[1][:-1])
        musique_id = int(line[0])

        if(user_id not in data_downloads.keys()):
            data_downloads[user_id] = []
        
        data_downloads[user_id].append(musique_id)
    
    for line in likes.readlines():
        line = line.split(",")
        user_id = int(line[1][:-1])
        musique_id = int(line[0])

        if(user_id not in data_likes.keys()):
            data_likes[user_id] = []
        
        data_likes[user_id].append(musique_id)
    
    for line in ecouter.readlines():
        line = line.split(",")
        user_id = int(line[1])
        musique_id = int(line[0])
        ecouter_nb = int(line[2][:-1])

        if(user_id not in data_ecoutes.keys()):
            data_ecoutes[user_id] = []
        
        data_ecoutes[user_id].append(musique_id)
        data_ecoutes[user_id].append(ecouter_nb)

    downloads.close()
    ecouter.close()
    likes.close()

    data_dict = dict()

    init_data_dict(data_dict, data_downloads, 0)

    init_data_dict(data_dict, data_ecoutes, 73)

    data_X = []
    data_Y = []

    max_len = get_max_len(data_likes)
    for key in data_dict.keys():
        likes_key = data_likes.keys()
        if(key in likes_key):
            value_like = data_likes[key]
            value_len = len(value_like)
            for i in value_like:
                temp_value = copy.copy(value_like)
                temp_value.remove(i)
                data_sample = [key] + data_dict[key] + temp_value
                
                data_sample = data_sample + [0 for j in range(max_len - value_len - 1)] 
                data_X.append(data_sample)
                data_Y.append(i)
                if(len(data_sample) != 533):
                    data_sample.pop()
            
    X_train, X_test, y_train, y_test = train_test_split(data_X, data_Y, test_size=0.2)

    knn = KNeighborsClassifier()
    knn.fit(X_train, y_train)
    
    y_predict = knn.predict(X_test)

    count = 0

    for i in range(len(y_predict)):
        if(y_predict[i] == y_test[i]):
            count += 1

    print('accuracy is %0.2f%%'%(100*count/len(y_predict)))
    
    print("user id :", user_id)
    print("value like :", value_like)
    print("value like predict :", y_predict)

    # X_train = np.asarray(X_train).astype(np.float32)
    # X_test = np.asarray(X_test).astype(np.float32)

    # y_train = np.asarray(y_train)
    # y_test = np.asarray(y_test)
    
    # model = create_model()

    # model.fit(X_train, y_train, batch_size = 32, epochs=30, validation_data=(X_test, y_test))