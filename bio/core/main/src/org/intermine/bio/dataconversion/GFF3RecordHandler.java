package org.intermine.bio.dataconversion;

/*
 * Copyright (C) 2002-2010 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.intermine.bio.io.gff3.GFF3Record;
import org.intermine.metadata.ClassDescriptor;
import org.intermine.metadata.Model;
import org.intermine.xml.full.Item;
import org.intermine.xml.full.ItemFactory;
import org.intermine.xml.full.ReferenceList;

/**
 * Permits specific operations to be performed when processing an line of GFF3.
 * GFF3Converter sets the core items created in the handler, the handler can
 * alter, remove from or add to the set of items.
 *
 * @author Richard Smith
 */
public class GFF3RecordHandler
{
    protected Map<String, Item> items = new LinkedHashMap<String, Item>();
    protected List<Item> earlyItems = new ArrayList<Item>();
    protected List<String> parents = new ArrayList<String>();
    private Item sequence;
    protected Item analysis;
    private Model tgtModel;
    private ItemFactory itemFactory;
    private Item organism;
    private ReferenceList dataSetReferenceList = new ReferenceList("dataSets");
    private ReferenceList publicationReferenceList = new ReferenceList("publications");
    private Item tgtOrganism;
    protected Item tgtSequence;
    private int itemid = 0;
    private Item dataSource;
    private Item dataSet;

    /**
     * Construct with the model to create items in (for type checking).
     * @param tgtModel the model for which items will be created
     */
    public GFF3RecordHandler(Model tgtModel) {
        this.tgtModel = tgtModel;
    }

    /**
     * Method to perform additional operations for a GFF3Record.  Access to core items
     * is possible via getters() (sequence and computational analysis items cannot be
     * altered but are available read-only).  Additional items to store can be placed
     * in the items map, keys can be anything except a string starting with '_'.
     * @param record the GFF line being processed
     */
    public void process(GFF3Record record) {
        // empty
    }

    /**
     * Set the Map of GFF identifiers to Item identifier.  The Map should be used to get/add any
     * item identifiers for features used so that multiple Items aren't created for the same
     * feature.
     * @param identifierMap map from GFF ID to item identifier for all features
     */
    public void setIdentifierMap(Map<?, ?> identifierMap) {
        // empty
     }

    /**
     * Return the Model that was passed to the constructor.
     * @return the Model
     */
    public Model getTargetModel() {
        return tgtModel;
    }

    /**
     * Set sequence item created for this record, should not be edited in handler.
     * @param sequence the sequence item
     */
    public void setSequence(final Item sequence) {
        this.sequence = sequence;
    }

    /**
     * Return the sequence Item set by setSequence()
     * @return the sequence Item
     */
    protected Item getSequence() {
        return sequence;
    }

    /**
     * Set organism item, this is global across record handler and final
     * @param organism the organism item
     */
    public void setOrganism(final Item organism) {
        this.organism = organism;
    }

    /**
     * Return the organism Item set by setOrganism()
     * @return the organism Item
     */
    protected Item getOrganism() {
        return organism;
    }

    /**
     * Set the location item for this record.
     * @param location the location item
     */
    public void setLocation(Item location) {
        items.put("_location", location);
    }

    /**
     * Return the location Item set by setLocation()
     * @return the location Item
     */
    protected Item getLocation() {
        return (Item) items.get("_location");
    }

    /**
     * Clear the location item for this record.
     */
    public void clearLocation() {
        items.remove("_location");
    }

    /**
     * Set the feature item for this record.
     * @param feature the feature item
     */
    public void setFeature(Item feature) {
        items.put("_feature", feature);
    }

    /**
     * Return the feature Item set by setFeature()
     * @return the feature Item
     */
    protected Item getFeature() {
        return (Item) items.get("_feature");
    }

    /**
     * Remove the feature item that was set with setFeature().
     */
    protected void removeFeature() {
        items.remove("_feature");
    }

    /**
     * Add an DataSet Item to this handler, to be retrieved later with getDataSetReferenceList().
     * @param dataSet the data set
     */
    public void addDataSet(Item dataSet) {
        dataSetReferenceList.addRefId(dataSet.getIdentifier());
    }

    /**
     * Return a ReferenceList containing the DataSet Items ids set by addDataSet()
     * @return the ReferenceList
     */
    public ReferenceList getDataSetReferenceList() {
        return dataSetReferenceList;
    }

    /**
     * Reset the list of DataSet items.
     */
    public void clearDataSetReferenceList() {
        dataSetReferenceList = new ReferenceList("dataSets");
    }

    /**
     * Add an Publication Item to this handler, to be retrieved later with
     * getPublicationReferenceList().
     * @param publication the data set
     */
    public void addPublication(Item publication) {
        publicationReferenceList.addRefId(publication.getIdentifier());
    }

    /**
     * Return a ReferenceList containing the Publication Items ids set by addPublication()
     * @return the ReferenceList
     */
    public ReferenceList getPublicationReferenceList() {
        return publicationReferenceList;
    }

    /**
     * Reset the list of Publication items.
     */
    public void clearPublicationReferenceList() {
        publicationReferenceList = new ReferenceList("publications");
    }

     /**
     * Get the tgtOrganism item to use in this handler
     * @param tgtOrganism the tgtOrganism item
     */
    public void setTgtOrganism(Item tgtOrganism) {
        items.put("_tgtOrganism", tgtOrganism);
    }

    /**
     * Return the tgtOrganism Item set by setTgtOrganism()
     * @return the tgtOrganism Item
     */
    protected Item getTgtOrganism() {
        return tgtOrganism;
    }

    /**
     * Set tgtSequence item created for this record, should not be edited in handler.
     * @param tgtSequence the sequence item
     */
    public void setTgtSequence(Item tgtSequence) {
        items.put("_tgtSequence", tgtSequence);
    }

    /**
     * Get the target Sequence Item set by setTgtSequence().
     * @return the target Sequence Item
     */
    protected Item getTgtSequence() {
        return tgtSequence;
    }

    /**
     * Set the tgtLocation item for this record.
     * @param tgtLocation the location item
     */
    public void setTgtLocation(Item tgtLocation) {
        items.put("_tgtLocation", tgtLocation);
    }

    /**
     * Return the tgtLocation Item set by setTgtLocation()
     * @return the tgtLocation Item
     */
    protected Item getTgtLocation() {
        return (Item) items.get("_tgtLocation");
    }

    /**
     * Return true if Location objects should be made for all features (which is the default).
     * @param record the current feature
     * @return true if Location objects should be made
     */
    protected boolean createLocations(GFF3Record record) {
        return true;
    }

    /**
     * Set the ItemFactory to use in this handler.
     * @param itemFactory the ItemFactory
     */
    public void setItemFactory(ItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }

    /**
     * Get the ItemFactory for this handler.
     * @return the ItemFactory
     */
    protected ItemFactory getItemFactory() {
        return itemFactory;
    }

    /**
     * Remove all items held locally in handler.
     */
    public void clear() {
        items = new LinkedHashMap<String, Item>();
        sequence = null;
        earlyItems.clear();
    }

    /**
     * Return all items set and created in handler in this run - excludes sequence and
     * ComputationalAnalysis items.
     * @return a set of items
     */
    public Collection<Item> getItems() {
        Set<Item> all = new LinkedHashSet<Item>(items.values());
        Set<Item> retval = new LinkedHashSet<Item>();
        for (Item item : earlyItems) {
            if (all.remove(item)) {
                retval.add(item);
            } else {
                throw new RuntimeException("Found item in earlyItems but not items: " + item);
            }
        }
        retval.addAll(all);
        return retval;
    }

    /**
     * Add a new Item to the Collection returned by getItems().
     * @param item the Item to add
     */
    public void addItem(Item item) {
        items.put(item.getIdentifier(), item);
    }

    /**
     * Add a new Item to the Collection returned by getItems, at the beginning.
     * @param item the Item to add
     */
    public void addEarlyItem(Item item) {
        items.put(item.getIdentifier(), item);
        earlyItems.add(item);
    }

    /**
     * Return items that need extra processing that can only be done after all other GFF features
     * have been read.
     * @return extra Items
     */
    public Collection<?> getFinalItems() {
        return new ArrayList();
    }

    /**
     * Clear the list of final items.
     */
    public void clearFinalItems() {
        // do nothing
    }

    /**
     * @param parent item ID
     */
    public void addParent(String parent) {
        parents.add(parent);
    }

    /**
     * Given a map from class name to reference name populate the reference for
     * a particular class with the parents of any SimpleRelations.
     * @param references map from classname to name of reference/collection to populate
     */
    protected void setReferences(Map<?, ?> references) {
        Item feature = getFeature();

        // set additional references from parents according to references map
        String clsName = feature.getClassName();
        if (references.containsKey(clsName) && !parents.isEmpty()) {
            ClassDescriptor cld =
                tgtModel.getClassDescriptorByName(tgtModel.getPackageName() + "." + clsName);

            String refName = (String) references.get(clsName);
            Iterator<String> parentIter = parents.iterator();

            if (cld.getReferenceDescriptorByName(refName, true) != null) {
                String parentRefId = parentIter.next();
                feature.setReference(refName, parentRefId);
                if (parentIter.hasNext()) {
                    String primaryIdent  = feature.getAttribute("primaryIdentifier").getValue();
                    throw new RuntimeException("Feature has multiple relations for reference: "
                            + refName + " for feature: " + feature.getClassName()
                            + ", " + feature.getIdentifier() + ", " + primaryIdent);
                }
            } else if (cld.getCollectionDescriptorByName(refName, true) != null) {
                List<String> refIds = new ArrayList<String>();
                while (parentIter.hasNext()) {
                    refIds.add(parentIter.next());
                }
                feature.setCollection(refName, refIds);
            } else if (parentIter.hasNext()) {
                throw new RuntimeException("No '" + refName + "' reference/collection found in "
                        + "class: " + clsName + " - is map configured correctly?");
            }

        }
    }

    /**
     * Create an item with given className and item identifier
     * @param className the class to create
     * @param identifier the Item identifier of the new Item
     * @return the created item
     */
    private Item createItem(String className, String identifier) {
        return itemFactory.makeItem(identifier, className, "");
    }

    /**
     * Create an item with given className and a new unique identifier
     * @param className the class to create
     * @return the created item
     */
    public Item createItem(String className) {
        return createItem(className, createIdentifier());
    }

    /**
     * create item identifier
     * @return identifier
     */
    private String createIdentifier() {
        return "1_" + itemid++;
    }

    /**
     * Get the DataSource to use while processing.
     * @return the DataSource
     */
    public Item getDataSource() {
        return dataSource;
    }

    /**
     * Set the DataSource to use while processing.  The converter will store() the DataSource.
     * @param dataSource the DataSource
     */
    public void setDataSource(Item dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get the DataSet to use while processing.  The converter will store() the DataSet.
     * @return the DataSet
     */
    public Item getDataSet() {
        return dataSet;
    }

    /**
     * Set the DataSet to use while processing.  Called by the converter.
     * @param dataSet the DataSet
     */
    public void setDataSet(Item dataSet) {
        this.dataSet = dataSet;
    }


    /**
     * Create and add a synonym Item from the given information.
     * @param subject the subject of the new Synonym
     * @param type the Synonym type
     * @param value the Synonym value
     * @return the new Synonym Item
     */
    public Item addSynonym(Item subject, String type, String value) {
        Item synonym = getItemFactory().makeItem(null, "Synonym", "");
        synonym.setAttribute("type", type);
        synonym.setAttribute("value", value);
        synonym.setReference("subject", subject.getIdentifier());
        synonym.addToCollection("dataSets", getDataSet());
        addItem(synonym);
        return synonym;
    }
}
