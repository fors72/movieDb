package com.app.moviedb.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class SRAdapter<T>  extends RecyclerView.Adapter<SRAdapter.SRViewHolder<T>> {
    private List<T> list;
    public Context context;
    public LayoutInflater layoutInflater;
    private List<Pair<Class<? extends SRViewHolder<T>>,Integer>> holderTypeMap = new ArrayList<>();


    private List<Pair<Class<? extends SRViewHolder<T>>,Integer>> preHolders = getPreHolders();
    private List<Pair<Class<? extends SRViewHolder<T>>,Integer>> postHolders = getPostHolders();

    private int preCount;
    private int postCount;

    public RecyclerView recyclerView;


    public enum ChangeType{
        ADDED,
        MODIFIED,
        REMOVED
    }

    public static class ChangeObject<T>{
        ChangeType changeType;
        int oldPosition;
        int newPosition;
        T obj;
        public ChangeObject(ChangeType changeType,int oldPosition,int newPosition,T obj) {
            this.changeType = changeType;
            this.oldPosition = oldPosition;
            this.newPosition = newPosition;
            this.obj = obj;
        }
    }




    public abstract Pair<Class<? extends SRViewHolder<T>>,Integer> getHolderType(T object);

    public List<Pair<Class<? extends SRViewHolder<T>>,Integer>> getPreHolders(){
     return null;
    }
    public List<Pair<Class<? extends SRViewHolder<T>>,Integer>> getPostHolders(){
     return null;
    }

    public SRAdapter(Context context, List<T> list) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        preCount = preHolders == null ? 0 : preHolders.size();
        postCount = postHolders == null ? 0 : postHolders.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    public void renderChanges(ChangeObject<T> changeObject){
        switch (changeObject.changeType){
            case ADDED:
                addObject(changeObject.newPosition,changeObject.obj);
                break;
            case REMOVED:
                removeObject(changeObject.oldPosition);
                break;
            case MODIFIED:
                if (changeObject.oldPosition != changeObject.newPosition){
                    moveObject(changeObject.oldPosition,changeObject.newPosition);
                    recyclerView.scrollToPosition(0);
                }
                updateObject(changeObject.newPosition,changeObject.obj);
                break;
        }

    }

    public void addObject(int position,T obj){
        if (position < 0 || position > list.size() - 1){
            list.add(obj);
            notifyItemInserted(preCount + list.size() - 1);
        }else {
            list.add(position,obj);
            notifyItemInserted(preCount + position);
        }
    }

    public void addObjects(int position, List<T> obj){
        if (position < 0 || position > list.size() - 1){
            int oldSize = list.size();
            list.addAll(obj);
            notifyItemRangeInserted(preCount + oldSize,obj.size());
        }else {
            list.addAll(position,obj);
            notifyItemRangeInserted(preCount + position,obj.size());
        }
    }

    public void removeObject(int position){
        if (position >= 0 && position < list.size()) {
            list.remove(position);
            notifyItemRemoved(preCount + position);
        }
    }

    public void removeObjects(int position,int count){
        if (position >= 0 && position < list.size()) {
            list.removeAll(list.subList(position,position + count));
            notifyItemRangeRemoved(preCount + position,count);
        }
    }

    public void updateObject(int position,T obj){
        if (position >= 0 && position < list.size()) {
            if (obj != null){
                list.remove(position);
                list.add(position,obj);
            }
            notifyItemChanged(preCount + position);
        }
    }

    public void moveObject(int from,int to){
        T o = list.remove(from);
        list.add(to,o);
        notifyItemMoved(from,to);
    }


    @Override
    public int getItemViewType(int position) {
        Pair<Class<? extends SRViewHolder<T>>,Integer> holderClass;
        if (position < preCount){
            holderClass = preHolders.get(position);
        }else if (position >= preCount + list.size()){
            holderClass = postHolders.get(position - (preCount + list.size()));
        }else {
            holderClass = getHolderType(list.get(position - preCount));
        }
        if (holderTypeMap.indexOf(holderClass) == -1)holderTypeMap.add(holderClass);
        return holderTypeMap.indexOf(holderClass);

    }



    @NonNull
    @Override
    public SRViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Pair<Class<? extends SRViewHolder<T>>,Integer> classPair = holderTypeMap.get(viewType);
        View v = layoutInflater.inflate(classPair.second, parent, false);
        SRViewHolder<T> srViewHolder;
        try {
            srViewHolder = classPair.first.getConstructor(View.class,SRAdapter.class).newInstance(v,this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return srViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SRViewHolder<T> holder, int position) {
        if (position >= preCount && position < preCount + list.size()) {
            holder.bindHolder(list.get(position - preCount));
        }else {
            holder.bindHolder(null);
        }
    }



    @Override
    public int getItemCount() {
        return preCount + (list == null ? 0 : list.size()) + postCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list){
        this.list = list;
    }


    public static abstract class SRViewHolder<T> extends RecyclerView.ViewHolder {
        public SRAdapter adapter;

        public SRViewHolder(View itemView, SRAdapter adapter) {
            super(itemView);
            this.adapter = adapter;


        }
        public abstract void bindHolder(T object);
    }

}
