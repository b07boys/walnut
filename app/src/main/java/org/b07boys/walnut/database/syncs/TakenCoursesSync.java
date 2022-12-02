package org.b07boys.walnut.database.syncs;

import org.b07boys.walnut.database.DatabaseNodeSync;
import org.b07boys.walnut.database.adapters.TakenCoursesAdapter;
import org.b07boys.walnut.user.TakenCourses;

public class TakenCoursesSync extends DatabaseNodeSync <TakenCoursesAdapter> {

    private TakenCourses takenCourses;

    public TakenCoursesSync(TakenCourses takenCourses, String node, Class<TakenCoursesAdapter> clazz) {
        super(node, clazz);
        this.takenCourses = takenCourses;
    }

    @Override
    protected void childAdded(TakenCoursesAdapter object, String key) {

    }

    @Override
    protected void childChanged(TakenCoursesAdapter object, String key) {

    }

    @Override
    protected void childRemoved(TakenCoursesAdapter object, String key) {

    }


}
