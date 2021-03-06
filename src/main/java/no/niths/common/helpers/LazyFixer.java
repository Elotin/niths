package no.niths.common.helpers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import no.niths.common.misc.Accessor;
import no.niths.domain.Domain;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Class used too clear or fetch relations so the lazy
 * loading works correctly
 *
 */
public class LazyFixer<T> {

    private final Object[] varargsNull = new Object[] { null };

    /**
     * Sets any children in the list of domains to null
     * 
     * @param list
     *            the list of which the relations are to be cleared
     */
    public void clearRelations(List<T> list) {

        try {
            for (Object domain : list) {
                removeChild(domain, domain.getClass());
            }
        } catch (NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {

            e.printStackTrace();
        }
    }

    /**
     * Sets any 2nd level children to null
     * 
     * @param domain
     *            the domain of which 2nd level children are to be removed
     */
    @SuppressWarnings("unchecked")
    public void clearSubRelations(T domain) {
        Class<?> domainType = domain.getClass();

        outer: for (Field outerField : domainType.getDeclaredFields()) {
            Class<?> outerType = outerField.getType();
            String outerFieldName = outerField.getName();
            try {

                // The current domain's (collection) child will yield true
                if (Collection.class.isAssignableFrom(outerType)) {

                    // If the annotations indicate they are transient, skip to
                    // the next attribute
                    if (checkAnnotations(
                            outerField.getDeclaredAnnotations())) {
                        continue outer;
                    }

                    // Find the collection's getter method
                    Method outerMethod = domainType
                            .getMethod(
                                    generateAccessorHeader(outerFieldName,
                                            Accessor.GET), (Class<?>[]) null);
                    Object result = outerMethod.invoke(domain);
                    if (result != null) {
                        for (Domain innerCollection : (
                                Collection<Domain>) result) {
                            removeChild(innerCollection,
                                    innerCollection.getClass());
                        }
                    }
                } else if (Domain.class.isAssignableFrom(outerType)) {

                    if (checkAnnotations(outerField.getAnnotations())) {

                        // Nullify domain that are transient
                        domainType.getDeclaredMethod(
                                generateAccessorHeader(outerFieldName,
                                        Accessor.SET), outerType).invoke(
                                domain, varargsNull);

                        // Nullify all domains and collections in the domain
                    } else {
                        Method m = domainType.getMethod(
                                generateAccessorHeader(outerFieldName,
                                        Accessor.GET), (Class<?>[]) null);
                        Domain result = (Domain) m.invoke(domain);

                        if (result != null) {
                            removeChild(result, result.getClass()
                                    .getSuperclass());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void fetchChildren(List<T> list) {
        for (Object element : list) {
            if (element == null) {
                return;
            }
            Class<?> type0 = element.getClass();

            try {
                for (Field field : element.getClass().getDeclaredFields()) {
                    Class<?> type = field.getType();

                    if (!checkAnnotations(field.getAnnotations())
                            && (Collection.class.isAssignableFrom(type)
                                    || Domain.class
                                    .isAssignableFrom(type))) {

                        Method m = type0.getMethod(
                                generateAccessorHeader(field.getName(),
                                        Accessor.GET), (Class<?>[]) null);
                        Object result = m.invoke(element);
                        if (result != null) {
                            Class<?> resultClass = result.getClass();
                            if (Collection.class.isAssignableFrom(
                                    resultClass)) {
                                Collection<Domain> domains =
                                        (Collection<Domain>) result;
                                domains.size();
                            } else if (Domain.class
                                    .isAssignableFrom(resultClass)) {
                                Domain domain = (Domain) result;
                                domain.getId();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param annotations
     *            the field's annotations
     * @return whether the annotations indicate that the field is transient or
     *         not
     */
    private boolean checkAnnotations(Annotation[] annotations) {
        boolean isTransient = false;

        for (Annotation annotation : annotations) {
            Class<?> annotationType = annotation.annotationType();
            if (annotationType == JsonIgnore.class
                    || annotationType == XmlTransient.class) {
                isTransient = true;
            }
        }

        return isTransient;
    }

    private String generateAccessorHeader(String fieldName, Accessor accessor) {
        return String.format(
                "%s%s",
                accessor,
                Character.toUpperCase(fieldName.charAt(0))
                        + fieldName.substring(1));
    }

    /**
     * 
     * @param target
     *            the object to be set to null
     * @param type
     *            the class
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    private void removeChild(Object target, Class<?> type)
            throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {

        for (Field field : type.getDeclaredFields()) {
            Class<?> fieldType = field.getType();
            // Nullify any domain or collection
            if (Collection.class.isAssignableFrom(fieldType)
                    || Domain.class.isAssignableFrom(fieldType)) {
                Method m = type.getMethod(
                        generateAccessorHeader(field.getName(), Accessor.SET),
                        fieldType);
                m.invoke(target, varargsNull);
            }
        }
    }
}
