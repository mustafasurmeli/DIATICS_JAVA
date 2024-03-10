import Lives.*;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    static ArrayList<Animal> animals = new ArrayList<>();

    public static void main(String[] args) {
        Random random = new Random();
        generateLives(AnimalSpeices.SHEEP, 15, "female");
        generateLives(AnimalSpeices.SHEEP, 15, "male");
        generateLives(AnimalSpeices.COW, 5, "female");
        generateLives(AnimalSpeices.COW, 5, "male");
        generateLives(AnimalSpeices.CHICKEN, 10, "female");
        generateLives(AnimalSpeices.CHICKEN, 10, "male");
        generateLives(AnimalSpeices.WOLF, 5, "female");
        generateLives(AnimalSpeices.WOLF, 5, "male");
        generateLives(AnimalSpeices.LION, 4, "female");
        generateLives(AnimalSpeices.LION, 4, "male");
        Hunter hunter = new Hunter();
        hunter.xLocation=random.nextInt(500);
        hunter.yLocation=random.nextInt(500);
        animals.add(hunter);

        for (int i = 0; i < 1000; i++) {

            for (int j = 0; j < animals.size(); j++) {

                if (animals.get(j).isLive){
                    animals.get(j).process();
                    animalMove(animals.get(j));

                }
                //bazı durumlarda ,hayvanların çoğunun birbirilerine yakın alanda oluşması gibi,
                // hayvanların sayısı sürekli arttığı için ve durmadan artmaya devam ettiği için
                // aşağıdaki if bloğu ile bir taşıma kapasitesi ve hayvan sınırı ekledim,
                // bu sınırı artırabilir veya kaldırabilirsiniz.

               if (animals.size() >= 5000) {
                   System.out.println("Sınıra Ulaşılan Adım: "+i);
                   printAnimals();
                   System.exit(0);
                }

            }

        }
        printAnimals();

    }
    public static void printAnimals() {

        long livingAnimalCount = animals.stream()
                .filter(animal -> animal.isLive)
                .count();

        System.out.println(livingAnimalCount + " adet hayvan yaşıyor.");
        Map<AnimalSpeices, Long> livingAnimalCountsBySpecies = animals.stream()
                .filter(animal -> animal.isLive)
                .collect(Collectors.groupingBy(animal -> animal.speices, Collectors.counting()));

        
        livingAnimalCountsBySpecies.forEach((species, count) -> System.out.println(species + ": " + count));
    }
    public static void generateLive(AnimalSpeices animalSpeices){
        Random random = new Random();

        String gender = random.nextBoolean()?"female":"male";
        generateLives(animalSpeices,1,gender);
    }
    public static void generateLives(AnimalSpeices animalSpeices,int count,String gender){
        Random random= new Random();
        for (int i = 0; i < count; i++) {

                Animal animal = null;
                switch (animalSpeices){
                    case SHEEP:
                        animal = new Sheep();
                        break;
                    case COW:
                        animal = new Cow();
                        break;
                    case CHICKEN:
                        animal = new Chicken();
                        break;
                    case WOLF:
                        animal = new Wolf();
                        break;
                    case LION:
                        animal = new Lion();
                        break;
                }
               if (animal==null) {
                   return;
               }

                animal.gender=gender;
                animal.xLocation=random.nextInt(500);
                animal.yLocation=random.nextInt(500);
                animal.isLive=true;
                animals.add(animal);



        }
    }

    public static List<Animal> findAnimalsWithinDistance(Animal animal, double distanceThreshold) {
        List<Animal> nearbyAnimals = new ArrayList<>();
        for (int i = 0; i < animals.size(); i++) {
            Animal a = animals.get(i);
            if (a != animal && a.isLive && distance(animal, a) <= distanceThreshold) {
                nearbyAnimals.add(a);
            }

        }

        return nearbyAnimals;
    }
    public static double distance(Animal a, Animal b) {
        return Math.sqrt(Math.pow(a.xLocation - b.xLocation, 2) + Math.pow(a.yLocation - b.yLocation, 2));
    }
    public static void animalMove(Animal a){
        ;
        switch (a.speices){
            case WOLF:
                List<Animal> wolfHunts=findAnimalsWithinDistance(a,a.range);
                for (Animal animal:wolfHunts){
                    if(animal.speices==AnimalSpeices.SHEEP||animal.speices==AnimalSpeices.CHICKEN){
                        animal.isLive=false;
                    }
                }
                break;
            case HUNTER:
                findAnimalsWithinDistance(a,a.range).forEach(animal -> animal.isLive=false);
                break;
            case LION:
                List<Animal> lionHunts=findAnimalsWithinDistance(a,a.range);
                for (Animal animal:lionHunts){
                    if(animal.speices==AnimalSpeices.SHEEP||animal.speices==AnimalSpeices.COW){
                        animal.isLive=false;
                    }
                }
                break;
        }
        List<Animal> nearAnimals=findAnimalsWithinDistance(a,3);
        for(Animal animal:nearAnimals) {
            if (animal.speices == a.speices&&animal.gender!=a.gender) {
                generateLive(a.speices);
            }
        }
   }



}