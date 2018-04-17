import tensorflow as tf

x = tf.constant(3.1, name="x")
#y = tf.Variable(4, name="y")
f = tf.square(x)

with tf.Session() as sess:
    init = tf.global_variables_initializer()
    init.run()
    result = f.eval()
    print(result)

    graph_def = tf.get_default_graph().as_graph_def()
    tf.train.write_graph(graph_def, '../../../../../../src/main/resources/com/qml/usecases', 'ex01.pb', False)

    #tf.train.write_graph(graph_def, '/tmp', 'ex01.pb', False)
