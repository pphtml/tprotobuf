import numpy as np
import tensorflow as tf

x_data = np.random.rand(100).astype(np.float32)
print(x_data)

y_data = x_data * 3 + 2
y_data = np.vectorize(lambda y: y + np.random.normal(loc=0.0, scale=0.1))(y_data)
print(y_data)

print(zip(x_data, y_data)[0:5])

a = tf.Variable(1.0, name='a')
b = tf.Variable(0.2, name='b')
c = tf.Variable(1.234, name='c')
y = a * x_data + b + c
#c = tf.cast(b, tf.int64)
#c = tf.constant(123, dtype=tf.int64, name='c')

loss = tf.reduce_mean(tf.square(y - y_data, name='squareNode'), name='reduceMeanNode')

gradients = tf.gradients(loss, [a, b, c])

# optimizer = tf.train.GradientDescentOptimizer(0.5, name='gradiDescendNode')
# train = optimizer.minimize(loss, name='trainNode')

init = tf.global_variables_initializer()

graph_def = tf.get_default_graph().as_graph_def()
tf.train.write_graph(graph_def, '../../../../../../src/main/resources/com/qml/usecases', 'ex03.pb', False)

sess = tf.Session()
sess.run(init)

evals = sess.run([gradients])
print(evals)


# train_data = []
# for step in range(100):
#     evals = sess.run([train, a, b])[1:]
#     if step % 5 == 0:
#         print(step, evals)
#         train_data.append(evals)

train_writer = tf.summary.FileWriter('/tmp/ex03tb', sess.graph)
sess.close()

