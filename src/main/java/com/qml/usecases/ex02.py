import tensorflow as tf

w = tf.constant(3)
x = w + 2
y = x + 5
z = x * 3

with tf.Session() as sess:
    print(y.eval()) # 10
    print(z.eval()) # 15

    graph_def = tf.get_default_graph().as_graph_def()
    tf.train.write_graph(graph_def, '../../../../../../src/main/resources/com/qml/usecases', 'ex02.pb', False)