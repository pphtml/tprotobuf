package org.superbiz.tf.type;

import org.superbiz.tf.QMLContext;
import org.superbiz.tf.annotation.Mapping;
import org.superbiz.tf.annotation.NamePrefix;
import org.superbiz.tf.annotation.OutputNodePostfix;
import org.superbiz.tf.annotation.Template;
import org.superbiz.tf.annotation.TemplateInline;
import org.superbiz.tf.attribute.Attribute;
import org.tensorflow.Output;

import java.util.List;
import java.util.stream.Collectors;

@NamePrefix("init")
@TemplateInline("node {\n" +
        "  name: \"${nodeName}\"\n" +
        "  op: \"NoOp\"\n" +
        "<#list variableList as variableName>" +
        "  input: \"${variableName}\"\n" +
        "</#list>" +
        "}")
public class Initializer extends AbstractNode implements TFType, NamingSequence {
    private List<String> variableNames;

    private Initializer(Attribute[] attributes) {
        super(attributes);
    }

    public static Initializer of(Attribute[] attributes) {
        return new Initializer(attributes);
    }

    @Override
    public void build(QMLContext qmlContext) {
        super.build(qmlContext);

        List<String> variableNames = qmlContext.getVariables().stream()
                .map(variable -> variable.getName())
                .map(name -> String.format("^%s/Assign", name))
                .collect(Collectors.toList());
        this.variableNames = variableNames;
    }

    @Mapping("variableList")
    public List<String> getVariableList() {
        return this.variableNames;
    }
}
