import numpy as np

def getPrediction(Theta1, Theta2, X):
#PREDICT Predict the label of an input given a trained neural network
#   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
#   trained weights of a neural network (Theta1, Theta2)

# Useful values
    m = np.shape(X)[0]              #number of examples
    
# You need to return the following variables correctly 
    p = np.zeros(m);

# ====================== YOUR CODE HERE ======================
# Instructions: Complete the following code to make predictions using
#               your learned neural network. You should set p to a 
#               vector containing labels between 1 to num_labels.
#
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
    

# =========================================================================
    return p

def sigmoid(z):
#SIGMOID Compute sigmoid functoon
#   J = SIGMOID(z) computes the sigmoid of z.

    g = 1/(1 + np.exp(-z))
    return g