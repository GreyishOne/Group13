/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group13;

/**
 *
 * @author frederikhelth
 */
public class Character {
    
    public String name;
    public String description;
    public String wants;
    public int mood;
    
    public Character(String name, String description, String wants){
        this.name = name;        
        this.description = description;
        this.wants = wants;
        this.mood = 0;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String setMood(int mood){
        this.mood = mood;
    }
    
    public String getNeeds(){
        return this.wants;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public int getMood(){
        return this.mood;
    }
    
}
