/**
 *
 * A2EmmaTran
 * COMP 2140 SECTION A01
 * INSTRUCTOR Cuneyt Akcora
 * ASSIGNMENT Assignment #2
 * @author Emma Tran, 7876069, tranmb@myumanitoba.ca
 * @version Monday, Feb 24th 2020
 * PURPOSE: To implement Link list merge, fix and conversion methods.
 *
 */


import java.util.*;

/******************************************
 *
 * LinkedList: A circular (singly-linked)
 * list with a last pointer and the number
 * of nodes.
 *
 ******************************************/

public class LinkedList
{
    public class Node
    {
        public int item;
        public Node next;

        public Node( int newItem, Node newNext )
        {
            item = newItem;
            next = newNext;
        }
    }


    private Node last = null;
    private int nodeCount;
    private static Random generator = new Random(System.nanoTime());

    public LinkedList()
    {
        nodeCount = 0;
    }

    public int getSize()
    {
        return nodeCount;
    }

    /*******************************************
     *
     *  insertNode: Insert a given node, newNode,
     *  into the list.  Since the list is
     *  unordered and inserting at the front of
     *  the list is easiest, we'll insert there.
     *
     *  Only special case: If the list is empty.
     *
     ********************************************/
    private void insertNode( Node newNode )
    {
        if ( nodeCount == 0 )
        {
            newNode.next = newNode;
            last = newNode;
        }
        else
        {
            newNode.next = last.next;
            last.next = newNode;
        }
        nodeCount++;
    }


    /*******************************************
     *
     *  insertValue: Insert a given item, newItem,
     *  into the list.  Since the list is
     *  unordered and inserting at the front of
     *  the list is easiest, we'll insert there.
     *
     *  Only special case: If the list is empty.
     *
     ********************************************/
    void insertValue(int newItem)
    {
        Node newNode = new Node( newItem, null );
        if ( nodeCount == 0 )
        {
            newNode.next = newNode;
            last = newNode;
        }
        else
        {
            newNode.next = last.next;
            last.next = newNode;
        }
        nodeCount++;
    }
    /*
    corrupt: expects an ordinary linked list with a null pointer in the last node.
    The latest inserted node sits at index 0.
    Take the last node in the linked list and change its pointer to the node at the given index.
    return the value of the corruption index.
     */
    /*******************************************
     *  Corrupt method
     ********************************************/
    public int corrupt(int index) {
        int valOfCorruptedNode=Integer.MIN_VALUE;
        int i = 0;
        Node curr = last;
        Node prev = null;
        do // loop through the linked list until reach the node at given index
        {
            prev = curr;
            curr = curr.next;
            i++;
        } while (i != index);

        //after get the node at given index, linked it with the end node of the linked list

        Node end = getLast(this);
        end.next = curr;
        valOfCorruptedNode = curr.item;
        return valOfCorruptedNode;
    }//end of corrupt

    /*
    findCorruption: find if the linked list contains a loop.
    Corruption is defined as the last node not having a null pointer, but instead pointing to a node in the linked
    list as its forward node. A circular link is due to a corruption.
    Do not use the sorted order of the linked list to detect the corruption.
    A popular interview question, the solution to this question is known as the Floyd’s Cycle-Finding Algorithm.
    A more difficult version would ask the index/value of the corruption.
     */
    /*******************************************
     *  findCorruption method
     ********************************************/
    public boolean findCorruption() {
        boolean result = false;
        Node pointer = last;
        Node end = getLast(this);
        if (end.next == null)  // if the end node has a null pointer, then the list is not corrupted
            result = false;
        else {    //else check is there any node that have the end node point to it

            do {
                pointer = pointer.next;
            } while (end.next != pointer); //loop until we meet the needed node, which has end node point to it
            if (end.next == pointer)
                result = true;
        }
        return result;
    }//end of findCorruption
    /*
    reset: Remove every node from the linked list. Do not forget the nodeCount variable.
     */
    /*******************************************
     *  reset method
     ********************************************/
    public void reset() {
        last = null; //set the top of the node to be null
        nodeCount = 0; //reset the nodeCount back to 0

    }// end of reset

    /*
    deleteOddNodes: delete nodes that have an odd item value.
    Reassign pointers. Do not create a new linked list.
     */
    /*******************************************
     *  deleteOddNodes() method
     ********************************************/
    public void deleteOddNodes() {
        Node curr = last;
        Node prev = null;

        while ( curr != null)     //loop through the linked list to check one by one node's value
        {
            if (curr.item %2 != 0)  //check if curr node is an odd number
            {
                nodeCount--;        // if curr node is an odd,
                                    //nodeCount will -1 for each time we find an odd node
                if (prev == null){      //the case when top node is odd
                    last = last.next;
                    curr = last;
                }
                else       //when other node is odd
                {
                    prev.next = curr.next;
                    curr = curr.next;
                }

            }
            else{ // when no meet odd node then just keep moving
                prev = curr;
                curr = curr.next;
            }

        }
    } //end of deleteOddNotes

    /*
    hasDummies: checks if the linked list has 1) a dummy header with Integer.MIN_VALUE
    and a dummy trailer with  Integer.MAX_VALUE.
     */
    /*******************************************
     *  hasDummies() method
     ********************************************/
    public boolean hasDummies() {
        boolean result = false;
        Node end = getLast(this);
        if (last.item == Integer.MIN_VALUE && end.item == Integer.MAX_VALUE) //last now is pointing to the top/head/first node
            result = true;

        return result;
    }//end of hasDummies


    /*
    isOrdinary: checks the type of this list.
    If the list is in a given type, it returns true, otherwise false.
    if the list empty, return false.
    Use the nodecount variable to detect the end
     */
    /*******************************************
     *  isOrdinary( ) method
     ********************************************/
    public boolean isOrdinary() {
        boolean result = false;
        Node curr = last;
        Node prev = null;
        int i = 1;
        do {
            prev = curr;
            curr = curr.next;
            i++;

        } while(i<nodeCount && curr.next !=null); //loop through the linked list until reach the the end
                                                  // the end detected by nodeCount and when curr.next reach null

        if(last != null && curr.next == null)   //if top != null and end node which is curr now pointer forward is null
        { result = true;}                       //then the result is true

        return result;
    }//end of isOrdinary

    /*
   isCircular: checks the type of this list.
   If the list is circular, it returns true, otherwise false.
   if the list empty, return false.
   Use the nodeCount variable to detect the end

    */
    /*******************************************
     *  isCircular( ) method
     ********************************************/
    public boolean isCircular( ) {
        Node curr = last.next; //last.next is my top node
        do {  //loop through the linked list
            curr = curr.next;
        }
        //if either top == null, curr == null then  stop the loop as it is false because there is no null pointer in circular linked list
        //also, if my top != end node (the last node in the list) then also stop the loop. After all, return if curr (my end node) == my top;
        //if equal then is circular => return true
        while ( last.next!=null && curr != null && curr != last.next);

        return (curr == last.next);
    }//end of isCircular


    /*
    addDummies: add a dummy header and a dummy trailer to the linked list.
    Use Integer.MIN_VALUE and Integer.MAX_VALUE
     */
    /*******************************************
     *  addDummies() method
     ********************************************/
    public void addDummies() {
        last.item = Integer.MIN_VALUE; //last is my top node, as im using Ordinary list now
        Node end = getLast(this); //end is the last node in the list
        end.item = Integer.MAX_VALUE;
    }//end of addDummies

    /***********************************************
     *
     * convertCircularToOrdinary:  Convert the list to an ordinary
     * linked list (a single linked list without dummy header or trailer).
     * use the existing last pointer and rewrite
     * its address to point to the first node. Update the last node's forward pointer as well.
     * This linked list class is originally written for a circular linked list,
     * DO NOT CHANGE THE ORIGINAL INSERT OR DELETE METHODS.
     ************************************************/
    public void convertCircularToOrdinary() {
        last = getHead(this); //getHead is a private helper method to get the pointer to my first/top node
                                   // assigned last pointer to first node
        Node end = getLast(this); //getLast is a private helper method to get the pointer to my the end/last node
        end.next=null;  //end forward pointer is null
    }

    private Node getHead(LinkedList list)
    {

        Node curr = last.next;
        Node prev = null;
        do {
            prev = curr;
            curr = curr.next;
        }
        while(curr != last.next);
        Node head = curr; // head is the first node

        return  head;

    }//end of convertCircularToOrdinary
    private Node getLast( LinkedList linkedList)
    {
        Node curr = last;
        Node prev = null;
        int i = 1;
        do {
            prev = curr;
            curr = curr.next;
            i++;

        } while(i<nodeCount && curr.next !=null);
        return curr;
    }

    /***********************************************
     * convertOrdinaryToCircular:  Convert the list to a circular
     * linked list (without a dummy header or trailer).
     ************************************************/
    public void convertOrdinaryToCircular() {
        Node prev =null;
        Node curr = last;
        do { //loop through the linked list until reach the last node, which has forward pointer to null
            prev = curr;
            curr = curr.next;
        } while(curr.next!= null);
        curr.next = last; // then set the last node forward pointer to the first node which is assigned to last pointer now
    }//end of convertOrdinaryToCircular


    /***********************************************
     *
     * add:  Take the values in the parameter list2
     * and insert them into this list in an order-preserving manner (keep the order as it is in list1).
     * The list2 should not change. Both lists are circular single linked lists (no dummies)
     *
     ************************************************/
    public void add(LinkedList list2) {
        //write your code here. List1 and list2 can contain duplicate values.
        LinkedList list1 = this;
        for(int i =0; i<list2.getSize();i++) //take the value of all node in list 2 start from top
        {
            int value = list2.last.next.item;
            list1.insertValue(value);    //insert these value to list 1
            last.next = last.next.next;
        }

    }//end of add


    /***********************************************
     *
     *  choosePivot:  Randomly choose a pivot from
     *  the list, unlink it from the list and return
     *  a pointer to it.
     *
     *  Assuming that the first node (last.next) is
     *  in position 0 and the last node is in
     *  position nodeCount-1, choose a random
     *  number, randomIndex, in the range 0 to
     *  nodeCount-1, and unlink the node in position
     *  randomIndex to be the pivot.
     *
     ************************************************/

    private Node choosePivot( )
    {
        int randomIndex = generator.nextInt( nodeCount );
        Node prev = last;
        Node pivot;
        int i;

        // Find the node before the pivot
        for ( i = 0; i <= randomIndex; i++ )
            prev = prev.next;

        pivot = prev.next;
        if ( last == pivot )
            last = prev;
        prev.next = pivot.next;
        nodeCount--;

        return pivot;
    } // end choosePivot


    /*******************************************
     *
     *  partition: Given a node, pivot (not in the
     *  list), leave all the nodes containing items
     *  >= the pivot's item in the list and move
     *  all nodes containing items < the pivot's item
     *  into a new LinkedList.  That is, at the end,
     *  the bigs remain in the list and the smalls
     *  are moved into a new list (which is returned).
     *
     *  Create two LinkedLists smalls and bigs.
     *  Move each node in the calling LinkedList
     *  into the smalls or the bigs list (compare
     *  each node's item to pivot.item and then
     *  unlink it from the calling list and
     *  use method insertNode() to put it into
     *  the smalls or bigs).
     *
     *  No Nodes should be created or destroyed by
     *  this method, and no item in a Node should
     *  be changed by this method.  This method
     *  simply unlinks and links existing nodes.
     *
     *********************************************/

    public LinkedList partition( Node pivot )
    {
        Node curr;
        LinkedList smalls = new LinkedList();
        LinkedList bigs = new LinkedList();
        int size = nodeCount;
        int i;

        for ( i = 0; i < size; i++ )
        {
            curr = last.next;
            last.next = last.next.next;
            nodeCount--;
            if ( curr.item < pivot.item )
            {
                // curr belongs in the smalls

                smalls.insertNode( curr );
            }
            else
            {
                // curr belongs in the bigs.

                bigs.insertNode( curr );
            }
        }
        last = bigs.last;
        nodeCount = bigs.nodeCount;

        return smalls;
    } // end partition

    /***********************************************
     *
     *  rejoin: Given the sorted smalls list and
     *  a pointer to the pivot, relink into the
     *  sorted list (which contains the sorted bigs)
     *  into the correct positions.
     *
     ************************************************/

    private void rejoin( LinkedList smalls, Node pivot )
    {
        Node firstBig;

        smalls.insertNode( pivot ); // insert pivot at front
        smalls.last = smalls.last.next; // pivot should be last node
        if ( nodeCount != 0 )
        {
            // There are some bigs.

            firstBig = this.last.next;
            this.last.next = smalls.last.next;
            pivot.next = firstBig;
            nodeCount = nodeCount + smalls.nodeCount;
        }
        else
        {
            // There are no bigs.

            this.last = smalls.last;
            nodeCount = smalls.nodeCount;
        }

    } // end rejoin

    /***********************************
     *
     * quickSort: Recursively quick sort a
     *    a circular linked list with no
     *    dummy nodes.
     *
     *   (Since the partition method
     *   puts the smalls into a new
     *   circular linked list, after the
     *   recursive calls, the smalls must
     *   be joined back into the list.)
     *
     **************************************/
    public void quickSort( )
    {
        Node pivot;
        LinkedList smalls;

        if ( nodeCount > 2 )
        {
            pivot = choosePivot(); // removes a pivot
            smalls = partition( pivot ); // bigs in original list
            smalls.quickSort(  );
            quickSort( ); // recursively quick sort the bigs
            rejoin( smalls, pivot ); // rejoin into one circular list
        }
        if ( nodeCount == 2 )
        {
            if ( last.item < last.next.item )
            {
                // swap them: first becomes last
                last = last.next;
            }
        }
        // else do nothing for nodeCount 0 or 1
    } // end quickSort

    /***********************************
     *
     * isSorted: Returns true if the list
     *  is sorted, false otherwise.
     *
     **************************************/

    public boolean isSorted()
    {
        boolean sorted = true;
        Node curr = last.next;

        if ( nodeCount > 1 )
        {
            do
            {
                if ( curr.next.item < curr.item )
                    sorted = false;
                curr = curr.next;
            } while ( ( curr  != last ) && sorted );
        }

        return sorted;
    } // end isSorted

    /***********************************
     *
     * printList: print out all the items
     *  in a list (for debugging purposes)
     *
     **************************************/

    public void printList()
    {
        Node curr;

        if (nodeCount > 0)
        {
            curr = last.next;
            do
            {
                System.out.println( curr.item );
                curr = curr.next;
            } while ( curr != last.next );
        }
    } // end printList




} // end class LinkedList
