package com.example.myapplication;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.model.Article;
import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> implements Filterable {

    private List<Article> articles;
    private List<Article> articlesFull; // A full copy of the articles list for filtering.

    public ArticlesAdapter(List<Article> articles) {
        this.articles = articles;
        this.articlesFull = new ArrayList<>(articles);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.name.setText(article.getName());
        holder.username.setText(article.getUsername());
        holder.email.setText(article.getEmail());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public Filter getFilter() {
        return articleFilter;
    }

    private Filter articleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Article> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(articlesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Article item : articlesFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) ||
                            item.getUsername().toLowerCase().contains(filterPattern) ||
                            item.getEmail().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            articles.clear();
            articles.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    public void updateArticles(List<Article> newArticles) {
        articles.clear();
        articles.addAll(newArticles);
        articlesFull.clear();
        articlesFull.addAll(newArticles);
        notifyDataSetChanged();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView name, username, email;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            username = itemView.findViewById(R.id.username);
            email = itemView.findViewById(R.id.email);
        }
    }
}
