import tensorflow as tf

x = tf.Variable(3, name="x")
y = tf.Variable(4, name="y")
f = x*x*y + y + 2

with tf.Session() as sess:
    # x.initializer.run()
    # y.initializer.run()
    init = tf.global_variables_initializer()
    init.run()
    result = f.eval()
    print(result)

    graph_def = tf.get_default_graph().as_graph_def()
    tf.train.write_graph(graph_def, '/tmp', 'ex01.pb', False)
