package com.example;

/**
 * Created by yanru.zhang on 16/9/20.
 * Email:yanru.zhang@renren-inc.com
 *
 * 循环有序数组的查找
 */
public class CyclicOrderedArray {

    public int find(int target,int[] array){
        if(array == null || array.length ==0) return -1;
        int i=0,j = array.length-1;
        if(array[i] >= array[j]){ //增3456789012
            while (i <= j){
                int mid = (i+j)/2;
                if(array[mid] >= array[i]){//在i-mid这一段是递增的,mid-j是循环递增数组
                    if(target == array[mid]){
                        return mid;
                    }else if(target < array[mid] && target >= array[i]){
                        j = mid-1;
                    }else{
                        i = mid+1;
                    }
                }else{//在mid-j这一段是递增的，i-mid是循环递增数组
                    if(target == array[mid]){
                        return mid;
                    }else if(target > array[mid] && target <= array[j]){
                        i = mid+1;
                    }else{
                        j = mid -1;
                    }
                }
            }
            return -1;
        }else{//减6543210987
            while (i<=j){
                int mid = (i+j)/2;
                if(array[mid] <= array[i]){//在i-mid递减，mid-j为循环递减数组
                    if(target == array[mid]){
                        return mid;
                    }else if(target <= array[i] && target > array[mid]){
                        j = mid-1;
                    }else{
                        i = mid+1;
                    }
                }else{//在mid-j递减，i-mid为循环递减数组
                    if(target == array[mid]){
                        return mid;
                    }else if(target < array[mid] && target >= array[j]){
                        i = mid+1;
                    }else{
                        j = mid-1;
                    }
                }
            }
            return -1;
        }
    }
}
