package org.superbiz.run;

import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import com.google.protobuf.InvalidProtocolBufferException;
import org.tensorflow.*;
import org.tensorflow.framework.GraphDef;

import java.io.File;
import java.io.IOException;

public class TensoRunExample {
    public static void main(String[] args) throws IOException {
        // System.out.println(System.getProperty("java.library.path"));
        doCounting();
    }

    public static void doCounting() throws IOException {
        try(Graph g = new Graph()){
            try(Tensor<Float> zeroT = Tensors.create(3.0f);
                Tensor<Float> stepT = Tensors.create(1.0f)){
                Output<Float> zero = g.opBuilder("Const", "start")
                        .setAttr("dtype", zeroT.dataType())
                        .setAttr("value", zeroT)
                        .build().output(0);
                Output<Float> step = g.opBuilder("Const", "step")
                        .setAttr("dtype", stepT.dataType())
                        .setAttr("value", stepT)
                        .build().output(0);
                Operation add = g.opBuilder("Add", "add")
                        .addInput(zero)
                        .addInput(step)
                        .build();

//                Output<Float> xVar = g.opBuilder("Variable", "x")
//                        .setAttr("dtype", zero.dataType())
//                        .setAttr("shape", zero.shape())
//                        .build().output(0);
//                Output<Float> x = g.opBuilder("Assign", "init_x")
//                        .addInput(xVar)
//                        .addInput(zero)
//                        .build().output(0);

//                Operation xUpdateOp = g.opBuilder("AssignAdd", "x_get_x_plus_step")
//                        .addInput(x)
//                        .addInput(step)
//                        .build();
//                Operation xUpdateOp =
//                        g.opBuilder("AssignAdd", "x_get_x_plus_step").addInput(xVar).addInput(step).build();

                try(Session s = new Session(g)) {
//                    s.runner().addTarget(x.op()).run();
//                    Session.Runner runner = s.runner().addTarget(xUpdateOp);
//                    runner.run();
//                    runner.run();
//                    s.runner().addTarget(xUpdateOp).run();
//                    s.runner().addTarget(xUpdateOp).run();

                    try(Tensor<Float> result = s.runner().fetch(add.name(), 0).run().get(0).expect(Float.class)){
                        System.out.println(result.floatValue());
                    }
                }

                byte[] bytes = g.toGraphDef();


                File file = new File("tensorun.pb");
                ByteSink sink = Files.asByteSink(file);
                sink.write(bytes);

                System.out.println(GraphDef.parseFrom(bytes));
            }
        }
    }
}
