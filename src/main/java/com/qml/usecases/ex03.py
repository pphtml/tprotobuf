import numpy as np
import tensorflow as tf

#x_data = np.random.rand(100).astype(np.float32)
x_data = [0.5084773, 0.45050743, 0.26249877, 0.42694545, 0.91235477, 0.06544802, 0.3362566, 0.8263691, 0.591857,
          0.97787154, 0.9754896, 0.21319687, 0.14495614, 0.97184163, 0.87484, 0.65439004, 0.38100997, 0.7957051,
          0.93941826, 0.9843869, 0.38828883, 0.92909086, 0.84629023, 0.64985895, 0.9267604, 0.19523112, 0.67092854,
          0.40732116, 0.07909536, 0.6977796, 0.6039431, 0.50627697, 0.40087187, 0.39805484, 0.6829685, 0.6030898,
          0.5765959, 0.33758417, 0.76656896, 0.57604396, 0.41675377, 0.93526024, 0.13862047, 0.648101, 0.617069,
          0.79915774, 0.4179804, 0.33842382, 0.6087638, 0.9144872, 0.06256474, 0.52752024, 0.7200489, 0.2947428,
          0.5073653, 0.47654673, 0.39477304, 0.63190114, 0.74356943, 0.7031697, 0.6964455, 0.7775143, 0.59867984,
          0.2671457, 0.5138146, 0.74707365, 0.60540545, 0.7542415, 0.5765414, 0.9381623, 0.97237116, 0.2863439,
          0.26354256, 0.7077794, 0.26621276, 0.7569819, 0.3138613, 0.9421915, 0.8239749, 0.2884851, 0.8364592,
          0.51019627, 0.9351209, 0.8446294, 0.5395264, 0.84271336, 0.97084665, 0.28346676, 0.00225191, 0.72276217,
          0.03537436, 0.8671024, 0.3697224, 0.8905249, 0.5418597, 0.13875394, 0.9339013, 0.3908971, 0.43483633,
          0.24581 ]
#print(x_data)

# y_data = x_data * 3 + 2
# y_data = np.vectorize(lambda y: y + np.random.normal(loc=0.0, scale=0.1))(y_data)
y_data = [3.60012087, 3.40591076, 2.8857028, 3.22463428, 4.78722158, 2.17033073, 3.09580803, 4.55211963, 3.70502823,
          4.89393018, 4.91716372, 2.94928737, 2.51394706, 5.13519628, 4.65976456, 3.9188956, 3.159699, 4.44534023,
          4.81331943, 4.97442608, 3.26097968, 4.5889248, 4.56280056, 3.80342606, 4.76527198, 2.7698204, 3.957877,
          3.28799015, 2.23208662, 4.22764853, 3.74747807, 3.55969059, 3.198679, 2.98834591, 4.0458736, 3.93836562,
          3.79242614, 3.0216278, 4.28957376, 3.85237813, 3.47936652, 4.75295462, 2.31443996, 3.87077037, 3.9296794,
          4.38574428, 3.17534438, 3.11335396, 3.77852262, 4.64968048, 2.1767642, 3.63471464, 4.06687023, 2.89278868,
          3.28672601, 3.29046424, 3.20139344, 4.08341168, 4.24755614, 4.0445099, 4.06186567, 4.35069294, 3.81153456,
          2.88951439, 3.5000918, 4.05995477, 3.66008351, 4.24301682, 3.71583879, 4.79210172, 4.76766244, 2.81925223,
          2.91727671, 4.08614234, 2.6590771, 4.36167308, 2.97663274, 4.73814542, 4.41897807, 2.8586536, 4.39075162,
          3.57420472, 4.77127177, 4.5666737, 3.64622612, 4.33933459, 4.92976979, 2.94056279, 1.86827826, 4.21800246,
          2.20864169, 4.55911266, 2.9195204, 4.80216273, 3.62375249, 2.29272854, 4.85654815, 3.23401777, 3.39311598,
          2.5310189 ]
#print(y_data)

#print(zip(x_data, y_data)[0:5])

a = tf.Variable(1.0, name='a')
b = tf.Variable(0.2, name='b')
#c = tf.Variable(1.234, name='c')
y = a * x_data + b
#c = tf.cast(b, tf.int64)
#c = tf.constant(123, dtype=tf.int64, name='c')

loss = tf.reduce_mean(tf.square(y - y_data, name='squareNode'), name='reduceMeanNode')

gradients = tf.gradients(loss, [a, b])

# optimizer = tf.train.GradientDescentOptimizer(0.5, name='gradiDescendNode')
# train = optimizer.minimize(loss, name='trainNode')

init = tf.global_variables_initializer()

graph_def = tf.get_default_graph().as_graph_def()
tf.train.write_graph(graph_def, '../../../../../../src/main/resources/com/qml/usecases', 'ex03.pb', False)

sess = tf.Session()
sess.run(init)

#evals = sess.run([gradients])

tensors = ['gradients/Fill:0', 'gradients/reduceMeanNode_grad/truediv:0',
  'gradients/squareNode_grad/Mul_1:0', 'gradients/squareNode_grad/Mul_1:0', 'gradients/sub_grad/Neg:0',
  'gradients/add_grad/Reshape:0', 'gradients/add_grad/Reshape_1:0', 'gradients/mul_grad/Reshape:0',
  'gradients/mul_grad/Reshape_1:0', 'gradients/add_grad/Reshape_1:0', 'gradients/mul_grad/Reshape:0' ]

for tensor in tensors:
    evals = sess.run(tensor)
#evals = sess.run('squareNode:0')
#evals = sess.run('reduceMeanNode:0')
    print('{}: {}\n\n'.format(tensor, evals))


# train_data = []
# for step in range(100):
#     evals = sess.run([train, a, b])[1:]
#     if step % 5 == 0:
#         print(step, evals)
#         train_data.append(evals)

train_writer = tf.summary.FileWriter('/tmp/ex03tb', sess.graph)
sess.close()

