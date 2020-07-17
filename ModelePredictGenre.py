import pandas as pd

from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier

if __name__ == "__main__":
    df = pd.read_csv('C:/Users/JULES LAMA/Documents/Dossier PA/data.csv', header = None, sep=',')
    df.drop(index=0,inplace=True)
    df = df.to_numpy()
    df_X, df_Y = df[:, 1:-1], df[:, -1]

    X_train, X_test, y_train, y_test = train_test_split(df_X, df_Y, test_size=0.1)

    
    knn = KNeighborsClassifier()
    knn.fit(X_train, y_train)

    
    y_predict = knn.predict(X_test)
    print('-----predict value is ------')
    print(y_predict)
    print('-----actual value is -------')
    print(y_test)
    count = 0

    
    for i in range(len(y_predict)):
        if(y_predict[i] == y_test[i]):
            count += 1
            
    print('accuracy is %0.2f%%'%(100*count/len(y_predict)))