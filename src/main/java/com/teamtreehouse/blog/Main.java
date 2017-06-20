package com.teamtreehouse.blog;

import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.SimpleBlogDao;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    private static final String FLASH_MESSAGE_KEY = "flash_message";

    public static void main(String[] args) {

        staticFileLocation("/");
        BlogDao dao = new SimpleBlogDao();

        before((request, response) -> {
            Map<String, Object> model = new HashMap<>();
            if (request.cookie("password") != null)
                model.put("password", request.cookie("password"));
            if (request.attribute("model") == null) {
                request.attribute("model", model);
            }
        });

        before("/new", (request, response) -> {
            Map<String,Object> model = request.attribute("model");
            if (model.get("password") == null) response.redirect("/password");
        });

        before("/edit/:slug", (request, response) -> {
            Map<String,Object> model = request.attribute("model");
            if (model.get("password") == null) response.redirect("/password");
        });

        before("/delete/:slug", (request, response) -> {
            Map<String,Object> model = request.attribute("model");
            if (model.get("password") == null) response.redirect("/password");
        });

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("entries", dao.findAllEntries());
            return new ModelAndView(model,"index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/index", (request, response) -> {
            response.redirect("/");
            return null;
        });

        get("/detail/:slug", (request, response) -> {
            Map<String,Object> model = new HashMap<>();
            model.put("blogEntry", dao.findEntryBySlug(request.params("slug")));
            model.put("flashMessage", captureFlashMessage(request));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        post("/detail/:slug", (request, response) -> {
            String name = request.queryParams("name");
            String comment = request.queryParams("comment");
            BlogEntry blogEntry = dao.findEntryBySlug(request.params("slug"));
            if (name.trim().isEmpty()) {name ="Anonymous";}
            if (comment.trim().isEmpty()) {
                setFlashMessage(request, "Please enter a valid comment");
                response.redirect(String.format("/detail/%s", blogEntry.getSlug()));
                halt();
            }
            blogEntry.addComment(new Comment(name, comment));
            response.redirect(String.format("/detail/%s", blogEntry.getSlug()));
            return null;
        });

        get("/new", (request, response) -> {
            Map<String,String> model = new HashMap<>();
            model.put("flashMessage", captureFlashMessage(request));
            return new ModelAndView(model, "new.hbs");
        }, new HandlebarsTemplateEngine());

        post("/new", (request, response) -> {
            String title = request.queryParams("title");
            String entry = request.queryParams("entry");
            if (title.trim().isEmpty()) {
                setFlashMessage(request, "Please enter a valid title");
                response.redirect("/new");
                halt();
            }
            if (entry.trim().isEmpty()) {
                setFlashMessage(request, "Please enter a valid entry");
                response.redirect("/new");
                halt();
            }
            dao.addEntry(new BlogEntry(title, entry));
            response.redirect("/");
            return null;
        });

        get("/delete/:slug", (request, response) -> {
            Map<String,Object> model = new HashMap<>();
            model.put("blogEntry", dao.findEntryBySlug(request.params("slug")));
            return new ModelAndView(model, "delete.hbs");
        }, new HandlebarsTemplateEngine());

        post("delete/:slug", (request, response) -> {
            BlogEntry blogEntry = dao.findEntryBySlug(request.params("slug"));
            dao.deleteEntry(blogEntry);
            response.redirect("/");
            return null;
        });

        get("/edit/:slug", (request, response) -> {
            Map<String,Object> model = new HashMap<>();
            model.put("blogEntry", dao.findEntryBySlug(request.params("slug")));
            model.put("flashMessage", captureFlashMessage(request));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edit/:slug", (request, response) -> {
            String title = request.queryParams("title");
            String entry = request.queryParams("entry");
            String date = String.format("%tA %<tB %<te, %<tY %<tr", new Date());
            BlogEntry blogEntry = dao.findEntryBySlug(request.params("slug"));
            if (title.trim().isEmpty()) {
                setFlashMessage(request, "Please enter a valid title");
                response.redirect(String.format("/edit/%s", blogEntry.getSlug()));
                halt();
            }
            if (entry.trim().isEmpty()) {
                setFlashMessage(request, "Please enter a valid entry");
                response.redirect(String.format("/edit/%s", blogEntry.getSlug()));
                halt();
            }
            blogEntry.setTitle(title);
            blogEntry.setEntry(entry);
            blogEntry.setDate(date);
            response.redirect(String.format("/detail/%s", blogEntry.getSlug()));
            return null;
        });

        get("/password", (request, response) -> {
            Map<String,String> model = new HashMap<>();
            model.put("flashMessage", captureFlashMessage(request));
            return new ModelAndView(model, "password.hbs");
        }, new HandlebarsTemplateEngine());

        post("/password", (Request request, Response response) -> {
            String password = request.queryParams("password");
            if (!password.equals(dao.getPassword())) {
                setFlashMessage(request, "Whoops, wrong password. Please try again!");
                response.redirect("/password");
                halt();
            }
            response.cookie("password", password);
            response.redirect("/");
            return null;
        });
    }

    private static void setFlashMessage(Request request, String message) {
        request.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    private static String getFlashMessage(Request request) {
        if (request.session(false) == null) {
            return null;
        }
        if (!request.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) request.session().attribute(FLASH_MESSAGE_KEY);
    }

    private static String captureFlashMessage(Request request) {
        String message = getFlashMessage(request);
        if (message != null) {
            request.session().removeAttribute(FLASH_MESSAGE_KEY);
        }
        return message;
    }
} 
