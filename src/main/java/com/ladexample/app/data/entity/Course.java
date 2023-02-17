package com.ladexample.app.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
public class Course{
   @Id
   @JsonProperty("ID")
   private Integer id;

   /*@Column
   @JsonProperty("Zulieferername")
   private String zuliefererName;

   @Column
   @JsonProperty("ZuliefererAnbieterid")
   private String zuliefererAnbieterId;

   @Column
   @JsonProperty("Zuliefererid")
   private String zuliefererId;

   @Column
   @JsonProperty("Anbieterid")
   private String anbieterId;
   */

   @Column
   @JsonProperty("Kurslink")
   private String kurslink;

   @Column
   @JsonProperty("Veranstaltungstyp")
   private String veranstaltungstyp;

   @Column(columnDefinition = "TEXT")
   @JsonProperty("Kurstitel")
   private String kurstitel;

   @Column(columnDefinition = "TEXT")
   @JsonProperty("Kursbeschreibung")
   private String kursbeschreibung;

   @Column
   @Temporal(TemporalType.DATE)
   @JsonProperty("Kursbeginn")
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
   private Date kursbeginn;

/*   @Column
   @JsonProperty("Schlagwort")
   private String schlagwort;*/

   @Column
   @JsonProperty("KursPLZ")
   private String kursPLZ;

   @Column
   @JsonProperty("Kursstadt")
   private String kursstadt;

   @Column
   @JsonProperty("Longitude")
   private Double longitude;

   @Column
   @JsonProperty("Latitude")
   private Double latitude;

   @Column
   @JsonProperty("Kursstrasse")
   private String kursstrasse;

/*   @Column
   @JsonProperty("Veranstaltername")
   private String veranstaltername;*/

   @Column
   @JsonProperty("Anbieterlink")
   private String anbieterlink;

/*   @Column
   @JsonProperty("Zuliefererlink")
   private String zuliefererlink;*/

   @Column
   @JsonProperty("Anbietername")
   private String anbietername;

   @Column
   @JsonProperty("Anbieterstrasse")
   private String anbieterstrasse;

   @Column
   @JsonProperty("AnbieterPLZ")
   private String anbieterPLZ;

   @Column
   @JsonProperty("Anbieterstadt")
   private String anbieterstadt;

   @Column
   @JsonProperty("Bundesland")
   private String bundesland;

/*   @Column
   @JsonProperty("Anbieterland")
   private String anbieterland;*/

   @Column
   @JsonProperty("Anbietertelefon1")
   private String anbietertelefon1;

/*   @Column
   @JsonProperty("Anbietertelefon2")
   private String anbietertelefon2;*/

   @Column
   @JsonProperty("Anbieterfax")
   private String anbieterfax;

/*   @Column
   @JsonProperty("Anbieteremail1")
   private String anbieteremail1;

   @Column
   @JsonProperty("Anbieteremail2")
   private String anbieteremail2;

   @Column
   @JsonProperty("Anbieterurl1")
   private String Anbieterurl1;

   @Column
   @JsonProperty("Anbieterurl2")
   private String Anbieterurl2;

   @Column
   @JsonProperty("Anbieterkommentar")
   private String anbieterkommentar;

   @Column
   @JsonProperty("Anbieterkontakt")
   private String anbieterkontakt;

   @Column
   @JsonProperty("istLehrerfortbildung")
   private String istLehrerfortbildung;

   @Column
   @JsonProperty("istBildungsurlaub")
   private String istBildungsurlaub;*/

   public String getKursbeschreibung(){
      return kursbeschreibung;
   }

   public String getBundesland(){
      return bundesland;
   }

   public Date getKursbeginn(){
      return kursbeginn;
   }

   public String getKurstitel(){
      return kurstitel;
   }

   public String getKursstadt(){
      return kursstadt;
   }

   public String getAnbietername(){
      return anbietername;
   }

   public Double getLatitude(){
      return latitude;
   }

   public Double getLongitude(){
      return longitude;
   }

   public String getAnbieterlink(){
      return anbieterlink;
   }

   @Override
   public int hashCode(){
      return id != null ? id.hashCode() : super.hashCode();
   }

   @Override
   public boolean equals(Object obj){
      if(!(obj instanceof Course other))
         return false; // null or other class

      if(id != null)
         return id.equals(other.id);
      return super.equals(other);
   }
}