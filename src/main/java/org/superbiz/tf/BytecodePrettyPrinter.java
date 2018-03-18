package org.superbiz.tf;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.TypePath;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class BytecodePrettyPrinter {
    /**
     * Gets us the bytecode method body priority a given method.
     * @param className The class name to search for.
     * @param methodName The method name.
     * @param methodDescriptor The method's descriptor.
     *                         Can be null if one wishes to just get the first
     *                         method with the given name.
     * @throws IOException
     */
    public static String[] getMethod(String className, String methodName, String methodDescriptor) throws IOException {
        ClassReader classReader = new ClassReader(className);
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(null, new SourceCodeTextifier(), printWriter);
        MethodSelectorVisitor methodSelectorVisitor = new MethodSelectorVisitor(traceClassVisitor, methodName, methodDescriptor);
        classReader.accept(methodSelectorVisitor, 0);

        return toList(stringWriter.toString());
    }

    /**
     * Gets us the bytecode method body priority a given method.
     * @param className The class name to search for.
     * @param methodName The method name.
     * @throws IOException
     */
    public static String[] getMethod(String className, String methodName) throws IOException {
        return getMethod(className, methodName, null);
    }

    private static String[] toList(String str) {
        //won't work correctly for all OSs
        String[] operations = str.split("[" + "\n" + "]");

        for (int i = 0; i < operations.length; ++i) {
            operations[i] = operations[i].trim();
        }

        return operations;
    }

    private static class MethodSelectorVisitor extends ClassVisitor {
        private final String methodName;
        private final String methodDescriptor;

        public MethodSelectorVisitor(ClassVisitor cv, String methodName, String methodDescriptor) {
            super(Opcodes.ASM4, cv);
            this.methodName = methodName;
            this.methodDescriptor = methodDescriptor;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                                         String signature, String[] exceptions) {

            if (methodName.equals(name)) {
                if (methodDescriptor == null)
                    return new MaxVisitFilterMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions));

                if (methodDescriptor.equals(desc))
                    return new MaxVisitFilterMethodVisitor(super.visitMethod(access, name, desc, signature, exceptions));
            }

            return null;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            return super.visitField(access, name, desc, signature, value);
        }

        @Override
        public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
            return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
        }

        @Override
        public void visitSource(String source, String debug) {
            super.visitSource(source, debug);
        }
    }

    private static class MaxVisitFilterMethodVisitor extends MethodVisitor {
        public MaxVisitFilterMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM4, mv);
        }

        @Override
        public void visitMaxs(int maxStack, int maxLocals) {
        }
    }


    private static class SourceCodeTextifier extends Printer {
        public SourceCodeTextifier() {
            this(Opcodes.ASM4);
        }

        protected SourceCodeTextifier(final int api) {
            super(api);
        }

        @Override
        public void visit(
                final int version,
                final int access,
                final String name,
                final String signature,
                final String superName,
                final String[] interfaces)
        {
        }

        @Override
        public void visitSource(final String file, final String debug) {
        }

        @Override
        public void visitOuterClass(
                final String owner,
                final String name,
                final String desc)
        {
        }

        @Override
        public Textifier visitClassAnnotation(
                final String desc,
                final boolean visible)
        {
            return new Textifier();
        }

        @Override
        public void visitClassAttribute(Attribute attribute) {

        }

//        @Override
//        public void visitClassAttribute(Attribute attribute) {
//
//        }
//
//        @Override
//        public void visitClassAttribute(final Attribute attr) {
//        }

        @Override
        public void visitInnerClass(
                final String name,
                final String outerName,
                final String innerName,
                final int access)
        {
        }

        @Override
        public Textifier visitField(
                final int access,
                final String name,
                final String desc,
                final String signature,
                final Object value)
        {
            return new Textifier();
        }

        @Override
        public Textifier visitMethod(
                final int access,
                final String name,
                final String desc,
                final String signature,
                final String[] exceptions)
        {
            Textifier t = new Textifier();
            text.add(t.getText());
            return t;
        }

        @Override
        public void visitClassEnd() {
        }

        @Override
        public void visit(final String name, final Object value) {
        }


        @Override
        public void visitEnum(
                final String name,
                final String desc,
                final String value)
        {
        }

        @Override
        public Textifier visitAnnotation(
                final String name,
                final String desc)
        {
            return new Textifier();
        }

        @Override
        public Textifier visitArray(
                final String name)
        {
            return new Textifier();
        }

        @Override
        public void visitAnnotationEnd() {
        }

        @Override
        public Textifier visitFieldAnnotation(
                final String desc,
                final boolean visible)
        {
            return new Textifier();
        }

        @Override
        public void visitFieldAttribute(final Attribute attr) {
            visitAttribute(attr);
        }

        @Override
        public void visitFieldEnd() {
        }

        @Override
        public Textifier visitAnnotationDefault() {
            return new Textifier();
        }

        @Override
        public Textifier visitMethodAnnotation(
                final String desc,
                final boolean visible)
        {
            return new Textifier();
        }

        @Override
        public Textifier visitParameterAnnotation(
                final int parameter,
                final String desc,
                final boolean visible)
        {
            return new Textifier();
        }

        @Override
        public void visitMethodAttribute(final Attribute attr) {
        }

        @Override
        public void visitCode() {
        }

        @Override
        public void visitFrame(
                final int type,
                final int nLocal,
                final Object[] local,
                final int nStack,
                final Object[] stack)
        {
        }

        @Override
        public void visitInsn(final int opcode) {
        }

        @Override
        public void visitIntInsn(final int opcode, final int operand) {
        }

        @Override
        public void visitVarInsn(final int opcode, final int var) {
        }

        @Override
        public void visitTypeInsn(final int opcode, final String type) {
        }

        @Override
        public void visitFieldInsn(
                final int opcode,
                final String owner,
                final String name,
                final String desc)
        {
        }

        @Override
        public void visitMethodInsn(
                final int opcode,
                final String owner,
                final String name,
                final String desc)
        {
        }

        @Override
        public void visitInvokeDynamicInsn(
                String name,
                String desc,
                Handle bsm,
                Object... bsmArgs)
        {
        }

        @Override
        public void visitJumpInsn(final int opcode, final Label label) {
        }

        @Override
        public void visitLabel(final Label label) {
        }

        @Override
        public void visitLdcInsn(final Object cst) {
        }

        @Override
        public void visitIincInsn(final int var, final int increment) {
        }

        @Override
        public void visitTableSwitchInsn(
                final int min,
                final int max,
                final Label dflt,
                final Label... labels)
        {
        }

        @Override
        public void visitLookupSwitchInsn(
                final Label dflt,
                final int[] keys,
                final Label[] labels)
        {
        }

        @Override
        public void visitMultiANewArrayInsn(final String desc, final int dims) {
        }

        @Override
        public void visitTryCatchBlock(
                final Label start,
                final Label end,
                final Label handler,
                final String type)
        {
        }

        @Override
        public void visitLocalVariable(
                final String name,
                final String desc,
                final String signature,
                final Label start,
                final Label end,
                final int index)
        {
            System.out.println("LOCAL VAR:");
        }

        @Override
        public void visitLineNumber(final int line, final Label start) {
            System.out.println("line number: ");
        }

        @Override
        public void visitMaxs(final int maxStack, final int maxLocals) {
        }

        @Override
        public void visitMethodEnd() {
        }

        public void visitAttribute(final Attribute attr) {
        }
    }

    public void bleble() {
        float a = 123;
        double b = 234;
        int i = 123;
    }

    public void test() {
        float a = 123;
        double b = 234;
        int i = 123;
        i = 123;

        float variableA = 123;
        double variableB = 234;
        System.out.println(variableA + variableB);
    }

    // https://www.programcreek.com/java-api-examples/?class=org.objectweb.asm.MethodVisitor&method=visitLocalVariable
    public static void main(String[] args) throws IOException {
        String[] ops = BytecodePrettyPrinter.getMethod("org.superbiz.tf.BytecodePrettyPrinter", "test", null);

        for (String op : ops)
            System.out.println(op);
    }

//    public static class MyClassVisitor extends ClassVisitor {
//
//        public MyClassVisitor(int api) {
//            super(api);
//        }
//
//        public MyClassVisitor(int api, ClassVisitor cv) {
//            super(api, cv);
//        }
//    }
}
