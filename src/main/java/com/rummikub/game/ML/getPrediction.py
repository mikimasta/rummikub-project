import scipy.io
import numpy as np

def getPrediction(X):

    trained_params = scipy.io.loadmat('???')
    Theta1 = trained_params['Theta1']
    Theta2 = trained_params['Theta2']
# Useful values
    m = np.shape(X)[0]              #number of examples
    
# You need to return the following variables correctly 
    p = np.zeros(m);

    # add a column of 1s to X for the bias term
    X = np.column_stack((np.ones((m, 1)), X))
    
    #compute activations for the hidden layer
    z2 = np.dot(X, Theta1.T)
    a2 = sigmoid(z2)

    #add a column of 1s to a2 for the bias term
    a2 = np.column_stack((np.ones((m, 1)), a2))

    #compute activations for the output layer
    z3 = np.dot(a2, Theta2.T)
    a3 = sigmoid(z3)

    # make predictions based on the highest activation
    p = np.argmax(a3, axis=1) + 1  # +1 because the labels are 1-indexed
    
    return p

def sigmoid(z):
    g = 1/(1 + np.exp(-z))
    return g