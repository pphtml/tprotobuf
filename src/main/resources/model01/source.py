import tensorflow as tf

h = tf.constant("Hello")
w = tf.constant(" World!")
hw = h + w

with tf.Session() as sess:
    ans = sess.run(hw)
    graph_def = tf.get_default_graph().as_graph_def()
    tf.train.write_graph(graph_def, '/tmp', 'helloworld.pb', False)

print(ans)