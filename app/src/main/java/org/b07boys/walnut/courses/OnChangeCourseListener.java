package org.b07boys.walnut.courses;

public interface OnChangeCourseListener {

    void onAdd(Course course);
    void onRemove(Course course);
    void onModify(Course course);

}
