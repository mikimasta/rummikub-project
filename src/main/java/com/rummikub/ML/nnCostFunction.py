import numpy as np


def nnCostFunction(nn_params, input_layer_size, hidden_layer_size, num_labels, X, y, lambda_value):

    tmp = nn_params.copy()
    Theta1 = np.reshape(tmp[0:hidden_layer_size * (input_layer_size + 1)], 
                          (hidden_layer_size, (input_layer_size + 1)), order='F')
    Theta2 = np.reshape(tmp[(hidden_layer_size * (input_layer_size + 1)):len(tmp)], 
                          (num_labels, (hidden_layer_size + 1)), order='F')

# Setup some useful variables
    m = np.shape(X)[0]

# Computation of the Cost function including regularisation
# Feedforward 
    a2 = sigmoid(np.dot(np.hstack((np.ones((m, 1)), X)), np.transpose(Theta1)))
    a3 = sigmoid(np.dot(np.hstack((np.ones((m, 1)), a2)), np.transpose(Theta2)))

    # Cost function for Logistic Regression summed over all output nodes
    Cost = np.empty((num_labels, 1))
    for k in range(num_labels):
        # which examples fit this label
        y_binary=(y==k+1)
        # select all predictions for label k
        hk=a3[:,k]
        # compute two parts of cost function for all examples for node k
        Cost[k][0] = np.sum(np.transpose(y_binary)*np.log(hk)) + np.sum(((1-np.transpose(y_binary))*np.log(1-hk)))
        
# Sum over all labels and average over examples
    J_no_regularisation = -1./m * sum(Cost)
# No regularization over intercept
    Theta1_no_intercept = Theta1[:, 1:]
    Theta2_no_intercept = Theta2[:, 1:]

# Sum all parameters squared
    RegSum1 = np.sum(np.sum(np.power(Theta1_no_intercept, 2)))
    RegSum2 = np.sum(np.sum(np.power(Theta2_no_intercept, 2)))
# Add regularisation term to final cost
    J = J_no_regularisation + (lambda_value/(2*m)) * (RegSum1+RegSum2)

# You need to return the following variables correctly 
    Theta1_grad = np.zeros(np.shape(Theta1))
    Theta2_grad = np.zeros(np.shape(Theta2))

    
    for t in range(m):
        # Feedforward pass
        a1 = X[t,:].reshape(1, -1)
        a1 = np.hstack((1, X[t])) # Add bias unit
        z2 = np.dot(Theta1, a1)
        a2 = sigmoid(z2)
        a2 = np.hstack((1, a2))  # Add bias unit
        z3 = np.dot(Theta2, a2)
        a3 = sigmoid(z3)

        # Step 2: Compute output layer delta
        yk = (y[t] == np.arange(1, num_labels + 1)).reshape(1, -1)
        delta3 = a3 - yk

        # Step 3: Compute hidden layer delta
        delta2 = np.dot(delta3, Theta2[:, 1:]) * gradiant(z2)

        # Step 4: Accumulate gradients
        Theta2_grad = Theta2_grad + np.outer(delta3, a2)
        Theta1_grad = Theta1_grad + np.outer(delta2, a1)

    # Step 5: Divide accumulated gradients by m
    Theta2_grad = Theta2_grad / m
    Theta1_grad = Theta1_grad / m

    # Add regularization term to gradients (skip regularization for bias terms)
    Theta2_grad[:, 1:] = Theta2_grad[:, 1:] + (lambda_value / m) * Theta2[:, 1:]
    Theta1_grad[:, 1:] = Theta1_grad[:, 1:] + (lambda_value / m) * Theta1[:, 1:]

# Unroll gradients
    Theta1_grad = np.reshape(Theta1_grad, Theta1_grad.size, order='F')
    Theta2_grad = np.reshape(Theta2_grad, Theta2_grad.size, order='F')
    grad = np.expand_dims(np.hstack((Theta1_grad, Theta2_grad)), axis=1)
    
    return J, grad


def sigmoid(z):
    g = 1/(1 + np.exp(-z))
    return g

def gradiant(z):
    g = 0
    sigmoidResult = sigmoid(z)
    g = sigmoidResult * (1 - sigmoidResult)
    
    return g