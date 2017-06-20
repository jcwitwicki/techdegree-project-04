package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import com.teamtreehouse.blog.model.NotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleBlogDao implements BlogDao{

    private List<BlogEntry> entries;
    public String password;

    public SimpleBlogDao() {
        entries = new ArrayList<>();
        password = "admin";

        template1();
        template2();
        template3();
    }

    @Override
    public List<BlogEntry> addEntry(BlogEntry blogEntry) {
        Collections.reverse(entries);
        entries.add(blogEntry);
        Collections.reverse(entries);
        return entries;
    }

    @Override
    public List<BlogEntry> deleteEntry(BlogEntry blogEntry) {
        entries.remove(blogEntry);
        return entries;
    }

    @Override
    public List<BlogEntry> findAllEntries() {
        return new ArrayList<>(entries);
    }

    @Override
    public BlogEntry findEntryBySlug(String slug) {
        return entries.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    public String getPassword() {
        return password;
    }

    public void template1() {
        BlogEntry template1 = new BlogEntry("The best day I’ve ever had", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at.\n" +
                "\n" + "Cras egestas ac ipsum in posuere. Fusce suscipit, libero id malesuada placerat, orci velit semper metus, quis pulvinar sem nunc vel augue. In ornare tempor metus, sit amet congue justo porta et. Etiam pretium, sapien non fermentum consequat, dolor augue gravida lacus, non accumsan lorem" +
                " odio id risus. Vestibulum pharetra tempor molestie. Integer sollicitudin ante ipsum, a luctus nisi egestas eu. Cras accumsan cursus ante, non dapibus tempor." );
        addEntry(template1);
        template1.setDate("Friday June 2, 2017 13:10:45");

        Comment comment = new Comment("Carling Kirk", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ut rhoncus felis, vel tincidunt neque. Vestibulum ut metus eleifend, malesuada nisl at, scelerisque sapien. Vivamus pharetra massa libero, sed feugiat turpis efficitur at." );
        template1.addComment(comment);
    }

    public void template2() {
        BlogEntry template2 = new BlogEntry("The absolute worst day I’ve ever had", "Ĉu vi iam scivolis, kion via tago estus kiel se vi ne havis matematiko? Se mi havus unu tagon sen matematiko," +
                " mi sentas kiel mi ne estis inteligentaj kaj ke mi apenaŭ povis fari ion ajn. Estus freneze tago elektro kaj iras butikumi. Mi vekiĝis unu belan matenon kun la suno brilis hele. Mi rigardis mian varman ..." );
        addEntry(template2);

        String tag1 = "dark";
        template2.addTag(tag1);
        String tag2 = "maths";
        template2.addTag(tag2);
        String tag3 = "esperanto";
        template2.addTag(tag3);

        template2.setDate("Tuesday June 6, 2017 17:55:32");
    }

    public void template3() {
        BlogEntry template3 = new BlogEntry("Dude, where’s my car?", "Jesse et Chester, deux fêtards d'une vingtaine d'années, ont passé une nuit incroyable. Malheureusement, à leur réveil, ils ne se rappellent plus de rien, y compris de l'endroit où ils ont garé leur voiture. Or, celle-ci contenait les précieux cadeaux d'anniversaire qu'ils avaient achetés pour leurs petites amies jumelles, Wanda et Wilma.\n" +
                "\n" + "Jesse et Chester ont beau leur expliquer que le véhicule, où se trouvaient les cadeaux, a disparu, elles ne veulent rien entendre. Une seule solution s'offre à eux : retourner sur leurs pas afin d'enquêter sur les événements de la veille et la disparition de leur voiture. Il s'est forcément passé quelque chose de spécial pour qu'ils n'aient plus souvenir de quoi que ce soit." +
                " Et comment se fait-il qu'ils soient devenus les coqueluches de toutes les filles ?");
        addEntry(template3);
        template3.setDate("Wednesday June 14, 2017 02:05:10");

        String tag1 = "comedy";
        template3.addTag(tag1);

        Comment comment2 = new Comment("Anonymous", "Bon nanard");
        template3.addComment(comment2);
        comment2.setDate("Wednesday June 14, 2017 08:31:48");
        Comment comment3 = new Comment("JC", "Affligeant");
        template3.addComment(comment3);
        comment3.setDate("Thursday June 15, 2017 15:27:26");
    }
} 
