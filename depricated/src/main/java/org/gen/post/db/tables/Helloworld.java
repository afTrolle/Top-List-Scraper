/*
 * This file is generated by jOOQ.
 */
package org.gen.post.db.tables;


import javax.annotation.Generated;

import org.gen.post.db.Public;
import org.gen.post.db.tables.records.HelloworldRecord;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Helloworld extends TableImpl<HelloworldRecord> {

    private static final long serialVersionUID = -807261089;

    /**
     * The reference instance of <code>public.helloworld</code>
     */
    public static final Helloworld HELLOWORLD = new Helloworld();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HelloworldRecord> getRecordType() {
        return HelloworldRecord.class;
    }

    /**
     * Create a <code>public.helloworld</code> table reference
     */
    public Helloworld() {
        this(DSL.name("helloworld"), null);
    }

    /**
     * Create an aliased <code>public.helloworld</code> table reference
     */
    public Helloworld(String alias) {
        this(DSL.name(alias), HELLOWORLD);
    }

    /**
     * Create an aliased <code>public.helloworld</code> table reference
     */
    public Helloworld(Name alias) {
        this(alias, HELLOWORLD);
    }

    private Helloworld(Name alias, Table<HelloworldRecord> aliased) {
        this(alias, aliased, null);
    }

    private Helloworld(Name alias, Table<HelloworldRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Helloworld(Table<O> child, ForeignKey<O, HelloworldRecord> key) {
        super(child, key, HELLOWORLD);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Helloworld as(String alias) {
        return new Helloworld(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Helloworld as(Name alias) {
        return new Helloworld(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Helloworld rename(String name) {
        return new Helloworld(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Helloworld rename(Name name) {
        return new Helloworld(name, null);
    }
}