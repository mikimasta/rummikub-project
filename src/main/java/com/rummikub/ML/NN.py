import scipy.io
import numpy as np

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

#Load pameters
# Load the weights into variables Theta1 and Theta2
mat = scipy.io.loadmat('/Users/milou/Desktop/ Machine Learning/Lab Six/Lab6_InitialCode/Initial code/debugweights.mat'); #insert debug here

# Unroll parameters
Theta1 = mat['Theta1']
Theta1_1d = np.reshape(Theta1, Theta1.size, order='F')
Theta2 = mat['Theta2']
Theta2_1d = np.reshape(Theta2, Theta2.size, order='F')

nn_params = np.hstack((Theta1_1d, Theta2_1d))


