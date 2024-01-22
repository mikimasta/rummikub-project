import scipy.io
import numpy as np

from fmincg import fmincg
from nnCostFunction import nnCostFunction

def randInitializeWeights(L_in, L_out):

    W = np.zeros((L_out, 1 + L_in))
    W = np.random.rand(L_out, 1 + L_in) * 0.24 - 0.12
    return W

def trainNN():

    # Setup the parameters you will use for this exercise
    input_layer_size = 104;     # each tile (not including jokers)
    hidden_layer_size = 25;     # 25 hidden units
    num_labels = 10;            # 10 labels, from 0 to 9   

    #Loading the data
    mat = scipy.io.loadmat('/Users/milou/Desktop/ Machine Learning/Lab Six/Lab6_InitialCode/Initial code/digitdata.mat') #insert real data here
    X = mat['X']
    y = mat['y']
    y = np.squeeze(y)
    m, _ = np.shape(X)


    #initialize paramaters 
    initial_Theta1 = randInitializeWeights(input_layer_size, hidden_layer_size)
    initial_Theta2 = randInitializeWeights(hidden_layer_size, num_labels)

    # Unroll parameters
    initial_Theta1 = np.reshape(initial_Theta1, initial_Theta1.size, order='F')
    initial_Theta2 = np.reshape(initial_Theta2, initial_Theta2.size, order='F')
    initial_nn_params = np.hstack((initial_Theta1, initial_Theta2))


    #Training Neural Network
    MaxIter = 150

    #  You should also try different values of lambda
    lambda_value = 1

    # Create "short hand" for the cost function to be minimized
    y = np.expand_dims(y, axis=1)

    costFunction = lambda p : nnCostFunction(p, input_layer_size, hidden_layer_size, 
                                            num_labels, X, y, lambda_value)

    # Now, costFunction is a function that takes in only one argument (the
    # neural network parameters)
    [nn_params, cost] = fmincg(costFunction, initial_nn_params, MaxIter)

    # Obtain Theta1 and Theta2 back from nn_params
    Theta1 = np.reshape(nn_params[0:hidden_layer_size * (input_layer_size + 1)], 
                                (hidden_layer_size, (input_layer_size + 1)), order='F')
    Theta2 = np.reshape(nn_params[((hidden_layer_size * (input_layer_size + 1))):],
                                (num_labels, (hidden_layer_size + 1)), order='F')
    
    scipy.io.savemat('/path/to/your/save/file/parameters.mat', {'Theta1': Theta1, 'Theta2': Theta2})

    
    return Theta1, Theta2


