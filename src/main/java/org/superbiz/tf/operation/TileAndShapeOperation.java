package org.superbiz.tf.operation;

import org.superbiz.tf.TF;
import org.superbiz.tf.annotation.*;
import org.superbiz.tf.attribute.Attribute;
import org.superbiz.tf.type.AbstractNode;
import org.superbiz.tf.type.NamingSequence;
import org.superbiz.tf.type.TFType;

public class TileAndShapeOperation {
    @TemplateInline("node {\n" +
            "  name: \"${nodeName}\"\n" +
            "  op: \"Tile\"\n" +
            "  input: \"${source}\"\n" +
            "  input: \"${multiples}\"\n" +
            "  attr {\n" +
            "    key: \"Tmultiples\"\n" +
            "    value {\n" +
            "      type: DT_INT32\n" +
            "    }\n" +
            "  }\n" +
            "  attr {\n" +
            "    key: \"T\"\n" +
            "    value {\n" +
            "      type: ${dType}\n" +
            "    }\n" +
            "  }\n" +
            "}\n")
    @NamePrefix("tile")
    //@ShapeTransformations({@ShapeTransformation("1,1->1"), @ShapeTransformation("1,N->N"), @ShapeTransformation("N,1->N"), })
    @ShapeTransformation("1,1->C")
    // @ShapeTransformation("N,N->N")
    public static class Tile extends AbstractNode implements TFType, NamingSequence {
        @TFInput
        @Mapping("source")
        public final TF<?, ?> source;
        @TFInput
        @Mapping("multiples")
        public final TF<?, ?> multiples;

        public <T extends TFType, R extends TFType, NTTypeSource, NTTypeMultiples> Tile(TF<T, NTTypeSource> source, TF<R, NTTypeMultiples> multiples, Attribute[] attributes) {
            super(attributes);
            this.source = source;
            this.multiples = multiples;
            this.setDType(this.source.getDType());
            this.setExpectedShape(null);
            super.postInit();
        }
    }
}
