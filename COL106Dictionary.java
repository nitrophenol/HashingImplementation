import java.util.LinkedList;

import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;

public class COL106Dictionary<K, V> {

    public LinkedList<DictionaryEntry<K, V>> dict;
    /*
     * dict is a Linked-List, where every node of linked-list is of type DictionaryEntry.
     * DictionaryEntry is a key-value pair, where the type of key and value is K and V respectively.
     */ 
    public LinkedList<HashTableEntry<K, V>>[] hashTable;
    /*
     * hashTable is an array of Linked-Lists which is initialized by the COL106Dictionary constructor.
     * Each index of hashTable stores a linked-list whose nodes are of type HashTableEntry
     * HashTableEntry is a key-address pair, where the type of key is K and the corresponding address is the address of the DictionaryEntry in the linked-list corresponding to the key of HashTableEntry
     */ 
    int length=0;
    // @SuppressWarnings("unchecked")
  int hashTableSize;
    COL106Dictionary(int hashTableSize) {
        dict = new LinkedList<DictionaryEntry<K, V>>();
        this.hashTableSize=hashTableSize;
        // This statement initiailizes a linked-list where each node is of type DictionaryEntry with key and value of type K and V respectively.
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        // This statement initiailizes the hashTable with an array of size hashTableSize where at each index the element is an instance of LinkedList class and
        // this array is type-casted to an array of LinkedList where the LinkedList contains nodes of type HashTableEntry with key of type K. 
        // for(int i=0;i<hashTableSize;i++)
        // {
        //     hashTable[i]=new LinkedList<HashTableEntry<K, V>>();
        // }
    }

    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K and it corresponding value of type V
         * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
         */
        if(key==null)
        {
            throw new NullKeyException();
        }


        int x=hash(key);
        if(hashTable[x]==null){
            hashTable[x] = new LinkedList<HashTableEntry<K, V>>();
        }
        int i;
        int flag=0;
        for(i=0;i<hashTable[x].size();i++)
        {
            if(hashTable[x].get(i).key.equals(key))
            {
                flag=1;
                break;
                
            }
        }
        if(flag==1)
        {
          throw new KeyAlreadyExistException();
        }
        DictionaryEntry<K,V> d=new DictionaryEntry<K,V>(key, value);
        HashTableEntry<K,V> h =new HashTableEntry<K,V>(key, d);
        hashTable[x].add(h);
        length++;
        dict.add(d);
    }

    public V delete(K key) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key
         * Working: Deletes the key-value pair from the Dictionary in O(1)
         */
        if(key==null)
        {
            throw new NullKeyException();
        }

        int x=hash(key);
        if(hashTable[x]==null){
            throw new KeyNotFoundException();
        }

        int i;
        int flag=0;
        for(i=0;i<hashTable[x].size();i++)
        {
            if(hashTable[x].get(i).key.equals(key))
            {
                flag=1;
                break;
                
            }
        }
        if(flag==0)
        {
          throw new KeyNotFoundException();
        }
            HashTableEntry<K,V> h = hashTable[x].get(i);
            DictionaryEntry<K,V> d=h.dictEntry;
            hashTable[x].remove(i); 
                   
            dict.remove(d);
            length--;
            
        return d.value ;

        
    }

    public V update(K key, V value) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the previously associated value of type V with the argumented key
         * Working: Updates the value associated with argumented key with the argumented value in O(1)
         */
        if(key==null)
        {
            throw new NullKeyException();
        }

        int x=hash(key);
        if(hashTable[x]==null){
            throw new KeyNotFoundException();
        }
        int i;
        int flag=0;
        for(i=0;i<hashTable[x].size();i++)
        {
            if(hashTable[x].get(i).key.equals(key))
            {
                flag=1;
                break;
                
            }
        }
        if(flag==0)
        {
          throw new KeyNotFoundException();
        }

        HashTableEntry<K,V> h = hashTable[x].get(i);
        DictionaryEntry<K,V> d=h.dictEntry;
        V r=d.value;
        d.value=value;
        
        return r;

    }

    public V get(K key) throws NullKeyException, KeyNotFoundException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */
        if(key==null)
        {
            throw new NullKeyException();
        }
        int x=hash(key);
        if(hashTable[x]==null){
            throw new KeyNotFoundException();
        }
        int i;
        int flag=0;
        for(i=0;i<hashTable[x].size();i++)
        {
            if(hashTable[x].get(i).key.equals(key))
            {
                flag=1;
                break;
            }
        }
        if(flag==0)
        {
            throw new KeyNotFoundException();
        }
        HashTableEntry<K,V> h = hashTable[x].get(i);
        DictionaryEntry<K,V> d=h.dictEntry;


        return d.value;

    
    }

    public int size() {
        /*
         * To be filled in by the student
         * Return: Returns the size of the Dictionary in O(1)
         */
        return length;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */
           K[] arr= (K[]) Array.newInstance(cls, dict.size());
           for (int j = 0; j < dict.size(); j++) {
                  arr[j]=dict.get(j).key;
           }
           return arr;
    }

    @SuppressWarnings("unchecked")
    public V[] values(Class<V> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */ V[] arr= (V[]) Array.newInstance(cls, dict.size());
         for (int j = 0; j < dict.size(); j++) {
            arr[j]=dict.get(j).value;
     }
     return arr;
    }

    public int hash(K key) {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the hash of the argumented key using the Polynomial Rolling
         * Hash Function.
         */
                
        String s=key.toString();
        int m1=0;
        int p=1;
         for (int i = 0; i < s.length(); i++) {
             
             m1=(m1+ ((int)s.charAt(i)+1)*p)%hashTableSize;
             p=(p*131)%hashTableSize;
 
         }
         return m1;
  
    }
    public boolean isthere(K key) {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */
         if (key==null) {
             return false;
         }

        int x=hash(key);
        if (hashTable[x]==null) {
            return false;
        }
        int i;
        boolean flag=false;
        // for(DictionaryEntry<K,V> d : dict){
        //     if(d.key.equals(key)){
        //         flag = true;
        //     }
        // }
        for(i=0;i<hashTable[x].size();i++)
        {
            if(hashTable[x].get(i).key.equals(key))
            {
                flag=true;
            }
        }
        return flag;

    
    }
      
  
}
