package Indexer.IndexerUtil;

import java.util.*;

/**
 * Created by rbtlong on 5/15/15.
 */
public class BinSearchList implements List<String> {
    static int cachedMid = -1;
    int dupelicates = 0;
    ArrayList<String> items = new ArrayList<>();

    public static int binarySearch(String key, ArrayList<String> a) {
        int lo = 0;
        int hi = a.size() - 1;
        while (lo <= hi) {
            int mid = cachedMid = lo + (hi - lo) / 2;
            if (key.compareTo(a.get(mid)) < 0) hi = mid - 1;
            else if (key.compareTo(a.get(mid)) > 0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static int binarySearchIgnoreCase(String key, ArrayList<String> a) {
        int lo = 0;
        int hi = a.size() - 1;
        while (lo <= hi) {
            int mid = cachedMid = lo + (hi - lo) / 2;
            if (key.compareToIgnoreCase(a.get(mid)) < 0) hi = mid - 1;
            else if (key.compareToIgnoreCase(a.get(mid)) > 0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof String)
            return binarySearch((String) o, items) == -1;
        return false;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof String) {
            int pos = binarySearch((String) o, items);
            if (pos != -1) {
                items.remove(pos);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(String s) {
        if (items.size() < 1) items.add(s);
        int pos = binarySearch(s, items);
        if (pos == -1) {
            int insPos = cachedMid;
            if (cachedMid > items.size()) insPos = items.size() - 1;
            items.add(insPos, s);
            return true;
        }
        return false;
    }

    @Override
    public int indexOf(Object o) {
        return binarySearch((String) o, items);
    }

    public int indexOfIgnoreCase(Object o) {
        return binarySearchIgnoreCase((String) o, items);
    }

    @Override
    public int lastIndexOf(Object o) {
        return items.lastIndexOf(o);
    }


    @Override
    public void add(int i, String s) {
        items.add(i, s);
    }

    @Override
    public String set(int i, String s) {
        return items.set(i, s);
    }

    @Override
    public Iterator<String> iterator() {
        return items.iterator();
    }

    @Override
    public Object[] toArray() {
        return items.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return items.toArray(ts);
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return items.containsAll(collection);
    }

    @Override
    public boolean addAll(Collection<? extends String> collection) {
        return items.addAll(collection);
    }

    @Override
    public boolean addAll(int i, Collection<? extends String> collection) {
        return items.addAll(i, collection);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public String get(int i) {
        return items.get(i);
    }

    @Override
    public String remove(int i) {
        return items.remove(i);
    }

    @Override
    public ListIterator<String> listIterator() {
        return items.listIterator();
    }

    @Override
    public ListIterator<String> listIterator(int i) {
        return items.listIterator(i);
    }

    @Override
    public List<String> subList(int i, int i1) {
        return items.subList(i, i1);
    }

}
