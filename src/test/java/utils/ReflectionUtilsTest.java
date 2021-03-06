package utils;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import utils.data.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class ReflectionUtilsTest {

    @Test
    public void getMapFieldNameAndValue_WhenExistFieldContent() {
        TestClass testClass = new TestClass();
        testClass.setName("nameName");
        testClass.setList(asList("one", "two"));
        Map<String, Object> map = ReflectionUtils.getMapFieldNameAndValue(testClass);

        HashMap<String, Object> mapExpected = new HashMap<>();
        mapExpected.put("name", "nameName");
        mapExpected.put("list", asList("one", "two"));

        Assertions.assertThat(map).isEqualTo(mapExpected);
    }

    @Test
    public void getMapFieldNameAndValue_WhenNullFieldContent() {
        TestClass testClass = new TestClass();
        Map<String, Object> map = ReflectionUtils.getMapFieldNameAndValue(testClass);

        HashMap<String, Object> mapExpected = new HashMap<>();
        mapExpected.put("name", null);
        mapExpected.put("list", null);

        Assertions.assertThat(map).isEqualTo(mapExpected);
    }

    @Test
    public void getMethod() {
        Optional<Method> optionalMethod = ReflectionUtils.getMethod(Child.class, "setId");
        Assertions.assertThat(optionalMethod.isPresent()).isTrue();
        Method method = optionalMethod.get();
        Assertions.assertThat(method.getName()).isEqualTo("setId");
    }

    @Test
    public void getAllMethodsInHierarchy_WhenCheckExistingParentMethods() {
        Method[] methods = ReflectionUtils.getAllMethodsInHierarchy(Child.class);
        List<String> methodName = Arrays.stream(methods)
                .map(Method::getName)
                .collect(toList());
        Assertions.assertThat(methodName).contains("setId");
        Assertions.assertThat(methodName.size()).isEqualTo(28);
    }

    @Test
    public void callMethod_WhenCallSetter() {
        Child child = new Child();
        ReflectionUtils.callMethod(child, "setId", 123L);
        Assertions.assertThat(child.getId()).isEqualTo(123L);
    }

    @Test
    public void getterByFieldName() {
        String name = ReflectionUtils.getterByFieldName("name");
        assertThat(name, equalTo("getName"));
    }

    @Test
    public void getterByFieldName_WhenBoolean() {
        String name = ReflectionUtils.getterByFieldName("isExist");
        assertThat(name, equalTo("getIsExist"));
    }

    @Test
    public void getterByFieldName_WhenEmpty() {
        String name = ReflectionUtils.getterByFieldName("");
        assertThat(name, equalTo(null));
    }

    @Test
    public void getterByFieldName_WhenSpaces() {
        String name = ReflectionUtils.getterByFieldName("     ");
        assertThat(name, equalTo(null));
    }

    @Test
    public void getterByFieldName_WhenNull() {
        String name = ReflectionUtils.getterByFieldName(null);
        assertThat(name, equalTo(null));
    }

    @Test
    public void setterByFieldName() {
        String name = ReflectionUtils.setterByFieldName("name");
        assertThat(name, equalTo("setName"));
    }

    @Test
    public void setterByFieldName_WhenBoolean() {
        String name = ReflectionUtils.setterByFieldName("isExist");
        assertThat(name, equalTo("setIsExist"));
    }

    @Test
    public void setterByFieldName_WhenEmpty() {
        String name = ReflectionUtils.setterByFieldName("");
        assertThat(name, equalTo(null));
    }

    @Test
    public void setterByFieldName_WhenSpaces() {
        String name = ReflectionUtils.setterByFieldName("   ");
        assertThat(name, equalTo(null));
    }

    @Test
    public void setterByFieldName_WhenNull() {
        String name = ReflectionUtils.setterByFieldName(null);
        assertThat(name, equalTo(null));
    }

    @Test
    public void getFieldContent_WhenNullField() {
        String name = "name";
        Base base = new Base(1L, name, new Date());
        String nameFieldContent = (String) ReflectionUtils.getFieldContent(base, null);

        assertThat(nameFieldContent, equalTo(null));
    }

    @Test
    public void getFieldContent_WhenNullObject() {
        String nameFieldContent = (String) ReflectionUtils.getFieldContent(null, "someField");

        assertThat(nameFieldContent, equalTo(null));
    }

    @Test
    public void getFieldContent() {
        String name = "name";
        Base base = new Base(1L, name, new Date());
        String nameFieldContent = (String) ReflectionUtils.getFieldContent(base, "name");

        assertThat(nameFieldContent, equalTo(name));
    }

    @Test
    public void getFieldContent_WhenParentField() {
        String nameParent = "nameParent";
        Child child = new Child(1L, nameParent, new Date(), "nameChild", 22);
        String nameFieldContentFromParent = (String) ReflectionUtils.getFieldContent(child, "name");

        assertThat(nameFieldContentFromParent, equalTo(nameParent));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFieldContent_WhenNotExistField() {
        String name = "name";
        Base base = new Base(1L, name, new Date());
        String nameFieldContent = (String) ReflectionUtils.getFieldContent(base, "notExist");

        assertThat(nameFieldContent, equalTo(name));
    }

    @Test
    public void setFieldContent_WhenObjNull() {
        String myName = "myName";
        Base base = new Base();
        ReflectionUtils.setFieldContent(null, "name", myName);

        assertThat(base.getName(), equalTo(null));
    }

    @Test
    public void setFieldContent_WhenFieldNull() {
        String myName = "myName";
        Base base = new Base();
        ReflectionUtils.setFieldContent(base, null, myName);

        assertThat(base.getName(), equalTo(null));
    }

    @Test
    public void setFieldContent() {
        String myName = "myName";
        Base base = new Base();
        ReflectionUtils.setFieldContent(base, "name", myName);

        assertThat(base.getName(), equalTo(myName));
    }

    @Test
    public void setFieldContent_WhenSetNull() {
        Base base = new Base();
        ReflectionUtils.setFieldContent(base, "name", null);

        assertThat(base.getName(), equalTo(null));
    }

    @Test
    public void setFieldContent_WhenParentField() {
        String myParentName = "myParent";
        Child child = new Child();
        ReflectionUtils.setFieldContent(child, "name", myParentName);

        assertThat(child.getName(), equalTo(myParentName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setFieldContent_WhenNotExistField() {
        String myName = "myName";
        Base base = new Base();
        ReflectionUtils.setFieldContent(base, "notExist", myName);

        assertThat(base.getName(), equalTo(myName));
    }

    @Test
    public void callMethod_WhenNullField() {
        String name = "name";
        Base base = new Base(1L, name, new Date());
        String getName = (String) ReflectionUtils.callMethod(base, null);

        assertThat(getName, equalTo(null));
    }

    @Test
    public void callMethod_WhenNullObject() {
        String getName = (String) ReflectionUtils.callMethod(null, "myMethod");
        assertThat(getName, equalTo(null));
    }

    @Test
    public void callMethod() {
        String name = "name";
        Base base = new Base(1L, name, new Date());
        String getName = (String) ReflectionUtils.callMethod(base, "getName");

        assertThat(getName, equalTo(name));
    }

    @Test(expected = IllegalArgumentException.class)
    public void callMethod_WhenNotExistMethod() {
        String name = "name";
        Base base = new Base(1L, name, new Date());
        String getName = (String) ReflectionUtils.callMethod(base, "notExistMethod");

        assertThat(getName, equalTo(name));
    }

    @Test
    public void callMethod_WhenFromCallParentMethod() {
        String nameParent = "nameParent";
        Child child = new Child(1L, nameParent, new Date(), "nameChild", 22);
        String getName = (String) ReflectionUtils.callMethod(child, "getName");

        assertThat(getName, equalTo(nameParent));
    }

    @Test
    public void getAllFields_WheExistStaticField() {
        Field[] allFields = ReflectionUtils.getAllFields(ObjWithStatic.class);

        // expected
        List<String> expected = asList("id", "PREFIX");

        List<String> listFieldName = Arrays.stream(allFields)
                .map(Field::getName)
                .collect(toList());

        assertThat(listFieldName, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void getAllFields_WhenNotInheritance() {
        Field[] allFields = ReflectionUtils.getAllFields(Base.class);

        // expected
        List<String> expected = asList("name", "id", "date");

        List<String> listFieldName = Arrays.stream(allFields)
                .map(Field::getName)
                .collect(toList());

        assertThat(listFieldName, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void getAllFields_WhenWithInheritance() {
        Field[] allFields = ReflectionUtils.getAllFields(Child.class);

        // expected
        List<String> expected = asList("name", "id", "date", "chileName", "age");

        List<String> listFieldName = Arrays.stream(allFields)
                .map(Field::getName)
                .collect(toList());

        assertThat(listFieldName, containsInAnyOrder(expected.toArray()));
    }

    @Test
    public void getAllFields_WhenNull() {
        Field[] allFields = ReflectionUtils.getAllFields(null);

        assertThat(allFields, equalTo(null));
    }

    @Test
    public void getAllFields_WhenClassWithoutFields() {
        Field[] allFields = ReflectionUtils.getAllFields(ClassWithoutFields.class);

        int FieldExpected[] = {};
        assertThat(allFields, equalTo(FieldExpected));
    }

    @Test
    public void getFieldFromObj() {
        Base base = new Base(1L, "name", new Date());
        Optional<Field> name = ReflectionUtils.getField(base, "name");

        assertThat(name.isPresent(), equalTo(true));
    }

    @Test
    public void getFieldFromObj_WhenNotExist() {
        Base base = new Base(1L, "name", new Date());
        Optional<Field> notExist = ReflectionUtils.getField(base, "notExist");

        assertThat(notExist.isPresent(), equalTo(false));
    }

    @Test
    public void getFieldFromObj_WhenNull() {
        Optional<Field> notExist = ReflectionUtils.getField(null, "notExist");

        assertThat(notExist.isPresent(), equalTo(false));
    }

    @Test
    public void getFieldFromClass() {
        Optional<Field> name = ReflectionUtils.getField(Base.class, "name");

        assertThat(name.isPresent(), equalTo(true));
    }

    @Test
    public void getFieldFromClass_WhenNotExist() {
        Optional<Field> notExist = ReflectionUtils.getField(Base.class, "notExist");

        assertThat(notExist.isPresent(), equalTo(false));
    }

    @Test
    public void castFieldValue_ToLong_WhenPassedInteger() {
        int fieldValue = 1;
        Object idValue = ReflectionUtils.castFieldValueByClass(Base.class, "id", fieldValue);
        assertThat(idValue, instanceOf(Long.class));
    }

    @Test
    public void castFieldValue_ToLong_WhenPassedFloat() {
        float fieldValue = 1f;
        Object idValue = ReflectionUtils.castFieldValueByClass(Base.class, "id", fieldValue);
        assertThat(idValue, instanceOf(Long.class));
    }

    @Test
    public void castFieldValue_ToLong_WhenPassedDouble() {
        double fieldValue = 1.;
        Object idValue = ReflectionUtils.castFieldValueByClass(Base.class, "id", fieldValue);
        assertThat(idValue, instanceOf(Long.class));
    }

    @Test
    public void castFieldValue_ToLong_WhenPassedStringNumber() {
        Object idValue = ReflectionUtils.castFieldValueByClass(Base.class, "id", "1");
        assertThat(idValue, instanceOf(Long.class));
    }

    @Test(expected = NumberFormatException.class)
    public void castFieldValue_ToLong_WhenPassedStringNumberWithDot() {
        Object idValue = ReflectionUtils.castFieldValueByClass(Base.class, "id", "1.1");
        assertThat(idValue, instanceOf(Long.class));
    }

    @Test(expected = NumberFormatException.class)
    public void castFieldValue_ToLong_WhenPassedStringNumberWithString() {
        Object idValue = ReflectionUtils.castFieldValueByClass(Base.class, "id", "1abs");
        assertThat(idValue, instanceOf(Long.class));
    }

    @Test(expected = NumberFormatException.class)
    public void castFieldValue_ToLong_WhenPassedStringNumber_ShouldThrowException() {
        Object idValue = ReflectionUtils.castFieldValueByClass(Base.class, "id", "abs");
        assertThat(idValue, instanceOf(Long.class));
    }

    @Test
    public void castFieldValue_ToDouble_WhenPassedStringNumber() {
        Object idValue = ReflectionUtils.castFieldValueByClass(SomeOther.class, "someDouble", "1");
        assertThat(idValue, instanceOf(Double.class));
    }

    @Test
    public void castFieldValue_ToDouble_WhenPassedStringNumberWithDot() {
        Object idValue = ReflectionUtils.castFieldValueByClass(SomeOther.class, "someDouble", "1.1");
        assertThat(idValue, instanceOf(Double.class));
    }

    @Test
    public void castFieldValue_ToBoolean_WhenPassedBool() {
        Object idValue = ReflectionUtils.castFieldValueByClass(SomeOther.class, "bool", false);
        assertThat(idValue, instanceOf(Boolean.class));
    }

    @Test
    public void castFieldValue_ToBoolean_WhenPassedNumber_Zero() {
        Object idValue = ReflectionUtils.castFieldValueByClass(SomeOther.class, "bool", 0);
        assertThat(idValue, instanceOf(Boolean.class));
    }

    @Test
    public void castFieldValue_ToBoolean_WhenPassedNumber() {
        Object idValue = ReflectionUtils.castFieldValueByClass(SomeOther.class, "bool", 0.2);
        assertThat(idValue, instanceOf(Boolean.class));
    }

    @Test
    public void castFieldValue_ToBoolean_WhenPassedString_Zero() {
        Object idValue = ReflectionUtils.castFieldValueByClass(SomeOther.class, "bool", "0");
        assertThat(idValue, instanceOf(Boolean.class));
    }

    @Test
    public void castFieldValue_ToBoolean_WhenPassedString_One() {
        Object idValue = ReflectionUtils.castFieldValueByClass(SomeOther.class, "bool", "1");
        assertThat(idValue, instanceOf(Boolean.class));
    }

    public static class TestClass {
        String name;
        List<String> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}