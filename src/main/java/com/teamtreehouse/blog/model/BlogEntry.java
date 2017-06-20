package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BlogEntry {

        private String slug;
        private String title;
        private String entry;
        private String date;
        private List<Comment> comments;
        private List<String> tags;

        public BlogEntry(String title, String entry) {
            this.title = title;
            this.entry = entry;
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
            date = String.format("%tA %<tB %<te, %<tY %<tr", new Date());
            this.comments = new ArrayList<>();
            this.tags = new ArrayList<>();
        }

        public void addComment(Comment comment) {
        Collections.reverse(comments);
        comments.add(comment);
        Collections.reverse(comments);
        }

        public void addTag(String tag) {
        tags.add(tag);
        }

        public String getSlug() {
            return slug;
        }

        public String getTitle() {
            return title;
        }

        public String getEntry() {
            return entry;
        }

        public String getDate() {
        return date;
    }

        public List<Comment> getComments() {
        return new ArrayList<>(comments);
        }

        public List<String> getTags() {
        return new ArrayList<>(tags);
    }

        public void setEntry(String entry) {
        this.entry = entry;
    }
        public void setDate(String date) {
        this.date = date;
    }
        public void setTitle(String title) {
        this.title = title;
    }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            BlogEntry blogEntry = (BlogEntry) o;

            if (title != null ? !title.equals(blogEntry.title) : blogEntry.title != null) return false;
            return entry != null ? entry.equals(blogEntry.entry) : blogEntry.entry == null;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (entry != null ? entry.hashCode() : 0);
            return result;
        }



}
