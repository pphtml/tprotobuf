import tensorflow as tf

x = tf.Variable(3.1, name="x")
y = tf.Variable(4.2, name="y")
f = x + y

with tf.Session() as sess:
    init = tf.global_variables_initializer()
    init.run()
    result = f.eval()
    print(result)

    graph_def = tf.get_default_graph().as_graph_def()
    tf.train.write_graph(graph_def, '/tmp', 'ex01f.pb', False)
