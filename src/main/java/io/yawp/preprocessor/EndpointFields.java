package io.yawp.preprocessor;

import com.google.appengine.labs.repackaged.com.google.common.collect.Iterables;
import com.google.common.base.CaseFormat;
import com.squareup.javapoet.*;

import io.yawp.repository.annotations.Endpoint;
import com.google.auto.service.AutoService;
import io.yawp.repository.query.FieldsRef;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Arrays;

@AutoService(Processor.class)
public class EndpointFields extends AbstractProcessor {

	private Types typeUtils;
	private Elements elementUtils;
	private Filer filer;
	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		messager = processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
		for (Element el : env.getElementsAnnotatedWith(Endpoint.class)) {
  			if (el.getKind() != ElementKind.CLASS) {
				error(el, "Only classes can be annotated with @%s", Endpoint.class.getCanonicalName());
			}

			TypeElement type = (TypeElement) el;
			String qualifiedName = type.getQualifiedName().toString() + "Fields";
			String simpleName = Iterables.getLast(Arrays.asList(qualifiedName.split("\\.")));
			String packageName = qualifiedName.substring(0, qualifiedName.length() - ("." + simpleName).length());

			TypeSpec.Builder enumBuilder = TypeSpec.enumBuilder(simpleName);

			enumBuilder.addModifiers(Modifier.PUBLIC);

			ClassName fieldsRef = ClassName.get(FieldsRef.class);
			enumBuilder.addSuperinterface(ParameterizedTypeName.get(fieldsRef, ClassName.get(type)));

			enumBuilder.addField(String.class, "originalName", Modifier.PRIVATE, Modifier.FINAL);
			enumBuilder.addMethod(MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PRIVATE)
				.addParameter(String.class, "originalName")
				.addStatement("this.$N = $N", "originalName", "originalName")
				.build());
			enumBuilder.addMethod(MethodSpec.methodBuilder("originalName")
					.addModifiers(Modifier.PUBLIC)
					.returns(String.class)
					.addStatement("return this.$N", "originalName")
					.build());

			for (Element child : el.getEnclosedElements()) {
				if (child.getKind() == ElementKind.FIELD) {
					String fieldName = child.getSimpleName().toString();
					String enumName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, fieldName);
					enumBuilder.addEnumConstant(enumName, TypeSpec.anonymousClassBuilder("$S", fieldName).build());
				}
			}

			try {
				JavaFile javaFile = JavaFile.builder(packageName, enumBuilder.build()).build();
				JavaFileObject jfo = filer.createSourceFile(qualifiedName);
				Writer writer = jfo.openWriter();
				javaFile.writeTo(writer);
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException("Couldn't write Fields class", e);
			}
		}
		return false;
	}

	private void error(Element e, String msg, Object... args) {
		messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return new LinkedHashSet<>(Arrays.asList(Endpoint.class.getCanonicalName()));
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}
}