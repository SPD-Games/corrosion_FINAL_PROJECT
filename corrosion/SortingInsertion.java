package corrosion;
import java.util.ArrayList;
import corrosion.entity.Entity;

public class SortingInsertion {
  /**
  * Variation of Bucket sort that sorts entity by zIndex
  * @param entities the arraylist to sort
  */
  public static ArrayList<Entity> bucketSort(ArrayList<Entity> entities){
    synchronized(entities){
      ArrayList<Entity>[] buckets = new ArrayList[10];
      for(int i = 0; i < 10; i++){
        buckets[i] = new ArrayList<Entity>();
      }
      while (entities.size() != 0){
        Entity e = entities.get(0);
        buckets[e.getZIndex()].add(e);
        entities.remove(0);
      }
      for(ArrayList<Entity> bucket : buckets){
        if (bucket != null){
          entities.addAll(bucket);
        }
      }
      return entities;
    }
  }

  public static ArrayList<Entity> insertByZIndex(ArrayList<Entity> entities, Entity e){
    synchronized(entities){
      int z = e.getZIndex();
      for (int i = 0; i < entities.size(); ++i){
        if (entities.get(i).getZIndex() >= z){
          entities.add(i,e);
          return entities;
        }
      }
      entities.add(e);
      return entities;
    }
  }

  //private int searchZIndex(ArrayList<Entity> entities, int z, int low, int high){
  //}
}
