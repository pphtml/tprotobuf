import tensorflow as tf

w = tf.constant(4, name='w')
y = tf.constant([1, 2, 3], name='y')
# w = tf.constant(4.4, name='w')
# y = tf.constant([1.1, 2.2, 3.3], name='y')
x = tf.divide(w, y)
# y = x + 5
# z = x * 3

with tf.Session() as sess:
    print(x.eval())
#    print(z.eval())

    graph_def = tf.get_default_graph().as_graph_def()
    tf.train.write_graph(graph_def, '../../../../../../src/main/resources/com/qml/usecases', 'ex02.pb', False)
