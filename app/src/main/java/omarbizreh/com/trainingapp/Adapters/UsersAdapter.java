package omarbizreh.com.trainingapp.Adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ObservableList.OnListChangedCallback;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;

import omarbizreh.com.trainingapp.BR;
import omarbizreh.com.trainingapp.DataModels.UserModel;
import omarbizreh.com.trainingapp.InstanceReferences;
import omarbizreh.com.trainingapp.R;

/**
 * Created by Omar on 1/16/2016.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private NotifyListChanged mChangeListener = new NotifyListChanged();

    public void CreateDragAndDrop(RecyclerView mRecyclerView) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            private Integer before = -1;

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG,
                        ItemTouchHelper.DOWN | ItemTouchHelper.UP);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Collections.swap(InstanceReferences.getUsers(), viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            }

            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                UsersAdapter.this.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

    private class NotifyListChanged extends OnListChangedCallback<ObservableList<UserModel>> {

        @Override
        public void onChanged(ObservableList<UserModel> sender) {

        }

        @Override
        public void onItemRangeChanged(ObservableList<UserModel> sender, int positionStart, int itemCount) {
            UsersAdapter.this.notifyItemChanged(positionStart);
        }

        @Override
        public void onItemRangeInserted(ObservableList<UserModel> sender, int positionStart, int itemCount) {
            UsersAdapter.this.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(ObservableList<UserModel> sender, int fromPosition, int toPosition, int itemCount) {
            UsersAdapter.this.notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<UserModel> sender, int positionStart, int itemCount) {
            UsersAdapter.this.notifyItemRemoved(positionStart);
        }
    }

    @Override
    public UsersAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        UsersAdapter.UserViewHolder viewHolder = new UsersAdapter.UserViewHolder(holder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UserViewHolder holder, int position) {
        holder.getViewBinding().setVariable(BR.user, InstanceReferences.getUsers().get(position));
        holder.getViewBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return InstanceReferences.getUsers().size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.CreateDragAndDrop(recyclerView);
        InstanceReferences.getUsers().addOnListChangedCallback(this.mChangeListener);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        InstanceReferences.getUsers().removeOnListChangedCallback(this.mChangeListener);
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mViewBinding;

        public UserViewHolder(View itemView) {
            super(itemView);
            this.mViewBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getViewBinding() {
            return this.mViewBinding;
        }
    }
}
