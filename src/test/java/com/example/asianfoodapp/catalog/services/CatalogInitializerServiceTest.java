package com.example.asianfoodapp.catalog.services;

import com.example.asianfoodapp.BaseIT;
import com.example.asianfoodapp.catalog.repository.RecipeRepository;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CatalogInitializerServiceTest extends BaseIT {

    @Value("${app.api-key}")
    private String API_KEY;

    @Autowired
    CatalogInitializerService catalogInitializerService;

    @Autowired
    private RecipeRepository repository;

    @Test
    public void testSaveRecipesFromAPI_Success(){
        //given
        String getRecipesIds = "/complexSearch" + "?cuisine=asian" +
                "&apiKey=" + API_KEY +
                "&offset=" + 0 +
                "&number=" + 2;
        wireMockServer.stubFor(WireMock.get(getRecipesIds)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "results": [
                                        {
                                            "id": 716426,
                                            "title": "Cauliflower, Brown Rice, and Vegetable Fried Rice",
                                            "image": "https://spoonacular.com/recipeImages/716426-312x231.jpg",
                                            "imageType": "jpg"
                                        },
                                        {
                                            "id": 642129,
                                            "title": "Easy To Make Spring Rolls",
                                            "image": "https://spoonacular.com/recipeImages/642129-312x231.jpg",
                                            "imageType": "jpg"
                                        }
                                    ],
                                    "offset": 0,
                                    "number": 2,
                                    "totalResults": 298
                                }
                                """.trim())));
        String getFirstRecipe = "/716426" +
                "/information?" +
                "includeNutrition=false&" +
                "apiKey=" + API_KEY;
        wireMockServer.stubFor(WireMock.get(getFirstRecipe)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "vegetarian": true,
                                    "vegan": true,
                                    "glutenFree": true,
                                    "dairyFree": true,
                                    "veryHealthy": true,
                                    "cheap": false,
                                    "veryPopular": true,
                                    "sustainable": false,
                                    "lowFodmap": false,
                                    "weightWatcherSmartPoints": 4,
                                    "gaps": "no",
                                    "preparationMinutes": -1,
                                    "cookingMinutes": -1,
                                    "aggregateLikes": 3689,
                                    "healthScore": 75,
                                    "creditsText": "Full Belly Sisters",
                                    "license": "CC BY-SA 3.0",
                                    "sourceName": "Full Belly Sisters",
                                    "pricePerServing": 112.39,
                                    "extendedIngredients": [
                                        {
                                            "id": 11090,
                                            "aisle": "Produce",
                                            "image": "broccoli.jpg",
                                            "consistency": "SOLID",
                                            "name": "broccoli",
                                            "nameClean": "broccoli",
                                            "original": "2 cups cooked broccoli, chopped small",
                                            "originalName": "cooked broccoli, chopped small",
                                            "amount": 2.0,
                                            "unit": "cups",
                                            "meta": [
                                                "cooked",
                                                "chopped"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 2.0,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 176.0,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11135,
                                            "aisle": "Produce",
                                            "image": "cauliflower.jpg",
                                            "consistency": "SOLID",
                                            "name": "cauliflower",
                                            "nameClean": "cauliflower",
                                            "original": "1 head of cauliflower, raw",
                                            "originalName": "cauliflower, raw",
                                            "amount": 1.0,
                                            "unit": "head",
                                            "meta": [
                                                "raw"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "head",
                                                    "unitLong": "head"
                                                },
                                                "metric": {
                                                    "amount": 1.0,
                                                    "unitShort": "head",
                                                    "unitLong": "head"
                                                }
                                            }
                                        },
                                        {
                                            "id": 4047,
                                            "aisle": "Baking",
                                            "image": "oil-coconut.jpg",
                                            "consistency": "LIQUID",
                                            "name": "t coconut oil",
                                            "nameClean": "coconut oil",
                                            "original": "1 + 1 T coconut oil or butter",
                                            "originalName": "1 T coconut oil or butter",
                                            "amount": 1.0,
                                            "unit": "",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "",
                                                    "unitLong": ""
                                                },
                                                "metric": {
                                                    "amount": 1.0,
                                                    "unitShort": "",
                                                    "unitLong": ""
                                                }
                                            }
                                        },
                                        {
                                            "id": 20041,
                                            "aisle": "Pasta and Rice",
                                            "image": "rice-brown-cooked.png",
                                            "consistency": "SOLID",
                                            "name": "brown rice",
                                            "nameClean": "cooked brown rice",
                                            "original": "3 cups of cooked brown rice, cold",
                                            "originalName": "cooked brown rice, cold",
                                            "amount": 3.0,
                                            "unit": "cups",
                                            "meta": [
                                                "cold",
                                                "cooked"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 3.0,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 585.0,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11215,
                                            "aisle": "Produce",
                                            "image": "garlic.png",
                                            "consistency": "SOLID",
                                            "name": "garlic",
                                            "nameClean": "garlic",
                                            "original": "5 cloves of garlic, chopped",
                                            "originalName": "garlic, chopped",
                                            "amount": 5.0,
                                            "unit": "cloves",
                                            "meta": [
                                                "chopped"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 5.0,
                                                    "unitShort": "cloves",
                                                    "unitLong": "cloves"
                                                },
                                                "metric": {
                                                    "amount": 5.0,
                                                    "unitShort": "cloves",
                                                    "unitLong": "cloves"
                                                }
                                            }
                                        },
                                        {
                                            "id": 4517,
                                            "aisle": "Oil, Vinegar, Salad Dressing",
                                            "image": "vegetable-oil.jpg",
                                            "consistency": "LIQUID",
                                            "name": "t grapeseed oil",
                                            "nameClean": "grape seed oil",
                                            "original": "1 + 1 T grapeseed oil",
                                            "originalName": "1 T grapeseed oil",
                                            "amount": 1.0,
                                            "unit": "",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "",
                                                    "unitLong": ""
                                                },
                                                "metric": {
                                                    "amount": 1.0,
                                                    "unitShort": "",
                                                    "unitLong": ""
                                                }
                                            }
                                        },
                                        {
                                            "id": 16424,
                                            "aisle": "Ethnic Foods",
                                            "image": "soy-sauce.jpg",
                                            "consistency": "LIQUID",
                                            "name": "soy sauce",
                                            "nameClean": "lower sodium soy sauce",
                                            "original": "3T reduced-sodium soy sauce",
                                            "originalName": "reduced-sodium soy sauce",
                                            "amount": 3.0,
                                            "unit": "T",
                                            "meta": [
                                                "reduced-sodium"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 3.0,
                                                    "unitShort": "Tbsps",
                                                    "unitLong": "Tbsps"
                                                },
                                                "metric": {
                                                    "amount": 3.0,
                                                    "unitShort": "Tbsps",
                                                    "unitLong": "Tbsps"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11304,
                                            "aisle": "Produce",
                                            "image": "peas.jpg",
                                            "consistency": "SOLID",
                                            "name": "peas",
                                            "nameClean": "petite peas",
                                            "original": "1 cup frozen peas",
                                            "originalName": "frozen peas",
                                            "amount": 1.0,
                                            "unit": "cup",
                                            "meta": [
                                                "frozen"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "cup",
                                                    "unitLong": "cup"
                                                },
                                                "metric": {
                                                    "amount": 145.0,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11304,
                                            "aisle": "Produce",
                                            "image": "peas.jpg",
                                            "consistency": "SOLID",
                                            "name": "peas",
                                            "nameClean": "petite peas",
                                            "original": "1 cup frozen peas",
                                            "originalName": "frozen peas",
                                            "amount": 1.0,
                                            "unit": "cup",
                                            "meta": [
                                                "frozen"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "cup",
                                                    "unitLong": "cup"
                                                },
                                                "metric": {
                                                    "amount": 145.0,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11304,
                                            "aisle": "Produce",
                                            "image": "peas.jpg",
                                            "consistency": "SOLID",
                                            "name": "peas",
                                            "nameClean": "petite peas",
                                            "original": "1 cup frozen peas",
                                            "originalName": "frozen peas",
                                            "amount": 1.0,
                                            "unit": "cup",
                                            "meta": [
                                                "frozen"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "cup",
                                                    "unitLong": "cup"
                                                },
                                                "metric": {
                                                    "amount": 145.0,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 2047,
                                            "aisle": "Spices and Seasonings",
                                            "image": "salt.jpg",
                                            "consistency": "SOLID",
                                            "name": "salt",
                                            "nameClean": "table salt",
                                            "original": "salt, to taste",
                                            "originalName": "salt, to taste",
                                            "amount": 8.0,
                                            "unit": "servings",
                                            "meta": [
                                                "to taste"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 8.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                },
                                                "metric": {
                                                    "amount": 8.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11291,
                                            "aisle": "Produce",
                                            "image": "spring-onions.jpg",
                                            "consistency": "SOLID",
                                            "name": "additional scallion tops",
                                            "nameClean": "spring onions",
                                            "original": "additional chopped scallion tops for garnish",
                                            "originalName": "additional chopped scallion tops for garnish",
                                            "amount": 8.0,
                                            "unit": "servings",
                                            "meta": [
                                                "chopped",
                                                "for garnish"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 8.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                },
                                                "metric": {
                                                    "amount": 8.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11291,
                                            "aisle": "Produce",
                                            "image": "spring-onions.jpg",
                                            "consistency": "SOLID",
                                            "name": "scallions",
                                            "nameClean": "spring onions",
                                            "original": "7 scallions, chopped (keep white/light green ends separate from dark green tops)",
                                            "originalName": "scallions, chopped (keep white/light green ends separate from dark green tops)",
                                            "amount": 7.0,
                                            "unit": "",
                                            "meta": [
                                                "dark",
                                                "green",
                                                "chopped",
                                                "(keep white/light ends separate from tops)"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 7.0,
                                                    "unitShort": "",
                                                    "unitLong": ""
                                                },
                                                "metric": {
                                                    "amount": 7.0,
                                                    "unitShort": "",
                                                    "unitLong": ""
                                                }
                                            }
                                        },
                                        {
                                            "id": 4058,
                                            "aisle": "Ethnic Foods",
                                            "image": "sesame-oil.png",
                                            "consistency": "LIQUID",
                                            "name": "sesame oil",
                                            "nameClean": "sesame oil",
                                            "original": "2t toasted sesame oil",
                                            "originalName": "toasted sesame oil",
                                            "amount": 2.0,
                                            "unit": "t",
                                            "meta": [
                                                "toasted"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.643,
                                                    "unitShort": "tsps",
                                                    "unitLong": "teaspoons"
                                                },
                                                "metric": {
                                                    "amount": 0.643,
                                                    "unitShort": "tsps",
                                                    "unitLong": "teaspoons"
                                                }
                                            }
                                        },
                                        {
                                            "id": 12023,
                                            "aisle": "Ethnic Foods",
                                            "image": "sesame-seeds.png",
                                            "consistency": "SOLID",
                                            "name": "sesame seeds",
                                            "nameClean": "sesame seeds",
                                            "original": "toasted sesame seeds, optional",
                                            "originalName": "toasted sesame seeds, optional",
                                            "amount": 8.0,
                                            "unit": "servings",
                                            "meta": [
                                                "toasted"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 8.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                },
                                                "metric": {
                                                    "amount": 8.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                }
                                            }
                                        }
                                    ],
                                    "id": 716426,
                                    "title": "Cauliflower, Brown Rice, and Vegetable Fried Rice",
                                    "readyInMinutes": 30,
                                    "servings": 8,
                                    "sourceUrl": "http://fullbellysisters.blogspot.com/2012/01/cauliflower-fried-rice-more-veggies.html",
                                    "image": "https://spoonacular.com/recipeImages/716426-556x370.jpg",
                                    "imageType": "jpg",
                                    "summary": "You can never have too many Chinese recipes, so give Cauliflower, Brown Rice, and Vegetable Fried Rice a try. This recipe serves 8 and costs $1.12 per serving. This hor d'oeuvre has <b>192 calories</b>, <b>7g of protein</b>, and <b>6g of fat</b> per serving. Head to the store and pick up broccoli, sesame seeds, soy sauce, and a few other things to make it today. 3689 people were impressed by this recipe. It is brought to you by fullbellysisters.blogspot.com. It is a good option if you're following a <b>gluten free, dairy free, lacto ovo vegetarian, and vegan</b> diet. From preparation to the plate, this recipe takes roughly <b>30 minutes</b>. Overall, this recipe earns a <b>spectacular spoonacular score of 100%</b>. If you like this recipe, you might also like recipes such as <a href=\\"https://spoonacular.com/recipes/cauliflower-brown-rice-and-vegetable-fried-rice-1584601\\">Cauliflower, Brown Rice, and Vegetable Fried Rice</a>, <a href=\\"https://spoonacular.com/recipes/cauliflower-brown-rice-and-vegetable-fried-rice-1238897\\">Cauliflower, Brown Rice, and Vegetable Fried Rice</a>, and <a href=\\"https://spoonacular.com/recipes/cauliflower-brown-rice-and-vegetable-fried-rice-1230097\\">Cauliflower, Brown Rice, and Vegetable Fried Rice</a>.",
                                    "cuisines": [
                                        "Chinese",
                                        "Asian"
                                    ],
                                    "dishTypes": [
                                        "side dish",
                                        "antipasti",
                                        "starter",
                                        "snack",
                                        "appetizer",
                                        "antipasto",
                                        "hor d'oeuvre"
                                    ],
                                    "diets": [
                                        "gluten free",
                                        "dairy free",
                                        "lacto ovo vegetarian",
                                        "vegan"
                                    ],
                                    "occasions": [],
                                    "winePairing": {
                                        "pairedWines": [
                                            "wine",
                                            "alcoholic drink",
                                            "white wine",
                                            "ingredient",
                                            "food product category",
                                            "riesling",
                                            "drink",
                                            "menu item type",
                                            "gewurztraminer",
                                            "chenin blanc"
                                        ],
                                        "pairingText": "Wine, Alcoholic Drink, and White Wine are my top picks for Vegetable Fried Rice. The best wine for Asian food depends on the cuisine and dish - of course - but these acidic whites pair with a number of traditional meals, spicy or not. You could try Perrier-Jouet Grand Brut. Reviewers quite like it with a 4.3 out of 5 star rating and a price of about 57 dollars per bottle.",
                                        "productMatches": [
                                            {
                                                "id": 430436,
                                                "title": "Perrier-Jouet Grand Brut",
                                                "description": "The striking freshness and vivacity of the floral and fruity fragrances (yellow fruits and fresh fruits) take root, before giving way to subtle notes of vanilla and butter, lending the wine a fruity and consistent character. The notes of cherry plum, lemon and bergamot orange emerge first, then make way for the flowers of fruit trees, such as lime and honeysuckle. These are followed by notes of butter, madeleine cakes and vanilla sugar. Lingering notes of grapefruit, white peaches, green pears, apple trees and green hazelnuts round off the taste.",
                                                "price": "$57.0",
                                                "imageUrl": "https://spoonacular.com/productImages/430436-312x231.jpg",
                                                "averageRating": 0.8600000143051147,
                                                "ratingCount": 36.0,
                                                "score": 0.8508257023785093,
                                                "link": "https://www.amazon.com/Perrier-Jou%C3%ABt-Champagne-France-Grand-750ml/dp/B072W5LQKS?tag=spoonacular-20"
                                            }
                                        ]
                                    },
                                    "instructions": "<ol><li><span></span>Remove the cauliflower's tough stem and reserve for another use. Using a food processor, pulse cauliflower florets until they resemble rice or couscous. You should end up with around four cups of \\"cauliflower rice.\\"</li><li>Heat 1T butter and 1T oil in a large skillet over medium heat. Add garlic and the white and light green pieces of scallion. Sauté about a minute.</li><li>Add the cauliflower to the pan. Stir to coat with oil, then spread out in pan and let sit; you want it cook a bit and to caramelize (get a bit brown), which will bring out the sweetness. After a couple of minutes, stir and spread out again.</li><li>Add cold rice (it separates easily, so it won't clump up during cooking), plus the additional grapeseed and coconut oil or butter. Raise heat to medium-high. Toss everything together and, again, spread the mixture out over the whole pan and press a bit into the bottom. Let it sit for about two minutes—so the rice can get toasted and a little crispy. Add the peas and broccoli and stir again. Drizzle soy sauce and toasted sesame oil over rice.</li><li>Cook for another minute or so and turn off heat. Add chopped scallion tops and toss.</li><li>I like to toast some sesame seeds in a dry pan; I sprinkle these and some more raw, chopped scallion over the top of the rice for added flavor and crunch.</li><li>Season to taste with salt and, if you'd like, more soy sauce. Keep in mind that if you're serving this with something salty and saucy (ie. teriyaki chicken) you may want to hold off on adding too much salt to the fried rice.</li></ol>",
                                    "analyzedInstructions": [
                                        {
                                            "name": "",
                                            "steps": [
                                                {
                                                    "number": 1,
                                                    "step": "Remove the cauliflower's tough stem and reserve for another use. Using a food processor, pulse cauliflower florets until they resemble rice or couscous. You should end up with around four cups of \\"cauliflower rice.\\"",
                                                    "ingredients": [
                                                        {
                                                            "id": 10011135,
                                                            "name": "cauliflower florets",
                                                            "localizedName": "cauliflower florets",
                                                            "image": "cauliflower.jpg"
                                                        },
                                                        {
                                                            "id": 10111135,
                                                            "name": "cauliflower rice",
                                                            "localizedName": "cauliflower rice",
                                                            "image": "cauliflower.jpg"
                                                        },
                                                        {
                                                            "id": 11135,
                                                            "name": "cauliflower",
                                                            "localizedName": "cauliflower",
                                                            "image": "cauliflower.jpg"
                                                        },
                                                        {
                                                            "id": 20028,
                                                            "name": "couscous",
                                                            "localizedName": "couscous",
                                                            "image": "couscous-cooked.jpg"
                                                        },
                                                        {
                                                            "id": 20444,
                                                            "name": "rice",
                                                            "localizedName": "rice",
                                                            "image": "uncooked-white-rice.png"
                                                        }
                                                    ],
                                                    "equipment": [
                                                        {
                                                            "id": 404771,
                                                            "name": "food processor",
                                                            "localizedName": "food processor",
                                                            "image": "food-processor.png"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "number": 2,
                                                    "step": "Heat 1T butter and 1T oil in a large skillet over medium heat.",
                                                    "ingredients": [
                                                        {
                                                            "id": 1001,
                                                            "name": "butter",
                                                            "localizedName": "butter",
                                                            "image": "butter-sliced.jpg"
                                                        },
                                                        {
                                                            "id": 4582,
                                                            "name": "cooking oil",
                                                            "localizedName": "cooking oil",
                                                            "image": "vegetable-oil.jpg"
                                                        }
                                                    ],
                                                    "equipment": [
                                                        {
                                                            "id": 404645,
                                                            "name": "frying pan",
                                                            "localizedName": "frying pan",
                                                            "image": "pan.png"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "number": 3,
                                                    "step": "Add garlic and the white and light green pieces of scallion. Sauté about a minute.",
                                                    "ingredients": [
                                                        {
                                                            "id": 11291,
                                                            "name": "green onions",
                                                            "localizedName": "green onions",
                                                            "image": "spring-onions.jpg"
                                                        },
                                                        {
                                                            "id": 11215,
                                                            "name": "garlic",
                                                            "localizedName": "garlic",
                                                            "image": "garlic.png"
                                                        }
                                                    ],
                                                    "equipment": []
                                                },
                                                {
                                                    "number": 4,
                                                    "step": "Add the cauliflower to the pan. Stir to coat with oil, then spread out in pan and let sit; you want it cook a bit and to caramelize (get a bit brown), which will bring out the sweetness. After a couple of minutes, stir and spread out again.",
                                                    "ingredients": [
                                                        {
                                                            "id": 11135,
                                                            "name": "cauliflower",
                                                            "localizedName": "cauliflower",
                                                            "image": "cauliflower.jpg"
                                                        },
                                                        {
                                                            "id": 0,
                                                            "name": "spread",
                                                            "localizedName": "spread",
                                                            "image": ""
                                                        },
                                                        {
                                                            "id": 4582,
                                                            "name": "cooking oil",
                                                            "localizedName": "cooking oil",
                                                            "image": "vegetable-oil.jpg"
                                                        }
                                                    ],
                                                    "equipment": [
                                                        {
                                                            "id": 404645,
                                                            "name": "frying pan",
                                                            "localizedName": "frying pan",
                                                            "image": "pan.png"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "number": 5,
                                                    "step": "Add cold rice (it separates easily, so it won't clump up during cooking), plus the additional grapeseed and coconut oil or butter. Raise heat to medium-high. Toss everything together and, again, spread the mixture out over the whole pan and press a bit into the bottom.",
                                                    "ingredients": [
                                                        {
                                                            "id": 4047,
                                                            "name": "coconut oil",
                                                            "localizedName": "coconut oil",
                                                            "image": "oil-coconut.jpg"
                                                        },
                                                        {
                                                            "id": 1001,
                                                            "name": "butter",
                                                            "localizedName": "butter",
                                                            "image": "butter-sliced.jpg"
                                                        },
                                                        {
                                                            "id": 0,
                                                            "name": "spread",
                                                            "localizedName": "spread",
                                                            "image": ""
                                                        },
                                                        {
                                                            "id": 20444,
                                                            "name": "rice",
                                                            "localizedName": "rice",
                                                            "image": "uncooked-white-rice.png"
                                                        }
                                                    ],
                                                    "equipment": [
                                                        {
                                                            "id": 404645,
                                                            "name": "frying pan",
                                                            "localizedName": "frying pan",
                                                            "image": "pan.png"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "number": 6,
                                                    "step": "Let it sit for about two minutes—so the rice can get toasted and a little crispy.",
                                                    "ingredients": [
                                                        {
                                                            "id": 20444,
                                                            "name": "rice",
                                                            "localizedName": "rice",
                                                            "image": "uncooked-white-rice.png"
                                                        }
                                                    ],
                                                    "equipment": [],
                                                    "length": {
                                                        "number": 2,
                                                        "unit": "minutes"
                                                    }
                                                },
                                                {
                                                    "number": 7,
                                                    "step": "Add the peas and broccoli and stir again.",
                                                    "ingredients": [
                                                        {
                                                            "id": 11090,
                                                            "name": "broccoli",
                                                            "localizedName": "broccoli",
                                                            "image": "broccoli.jpg"
                                                        },
                                                        {
                                                            "id": 11304,
                                                            "name": "peas",
                                                            "localizedName": "peas",
                                                            "image": "peas.jpg"
                                                        }
                                                    ],
                                                    "equipment": []
                                                },
                                                {
                                                    "number": 8,
                                                    "step": "Drizzle soy sauce and toasted sesame oil over rice.Cook for another minute or so and turn off heat.",
                                                    "ingredients": [
                                                        {
                                                            "id": 4058,
                                                            "name": "sesame oil",
                                                            "localizedName": "sesame oil",
                                                            "image": "sesame-oil.png"
                                                        },
                                                        {
                                                            "id": 16124,
                                                            "name": "soy sauce",
                                                            "localizedName": "soy sauce",
                                                            "image": "soy-sauce.jpg"
                                                        },
                                                        {
                                                            "id": 20444,
                                                            "name": "rice",
                                                            "localizedName": "rice",
                                                            "image": "uncooked-white-rice.png"
                                                        }
                                                    ],
                                                    "equipment": []
                                                },
                                                {
                                                    "number": 9,
                                                    "step": "Add chopped scallion tops and toss.I like to toast some sesame seeds in a dry pan; I sprinkle these and some more raw, chopped scallion over the top of the rice for added flavor and crunch.Season to taste with salt and, if you'd like, more soy sauce. Keep in mind that if you're serving this with something salty and saucy (ie. teriyaki chicken) you may want to hold off on adding too much salt to the fried rice.",
                                                    "ingredients": [
                                                        {
                                                            "id": 12023,
                                                            "name": "sesame seeds",
                                                            "localizedName": "sesame seeds",
                                                            "image": "sesame-seeds.png"
                                                        },
                                                        {
                                                            "id": 16124,
                                                            "name": "soy sauce",
                                                            "localizedName": "soy sauce",
                                                            "image": "soy-sauce.jpg"
                                                        },
                                                        {
                                                            "id": 11291,
                                                            "name": "green onions",
                                                            "localizedName": "green onions",
                                                            "image": "spring-onions.jpg"
                                                        },
                                                        {
                                                            "id": 5006,
                                                            "name": "whole chicken",
                                                            "localizedName": "whole chicken",
                                                            "image": "whole-chicken.jpg"
                                                        },
                                                        {
                                                            "id": 0,
                                                            "name": "sandwich bread",
                                                            "localizedName": "sandwich bread",
                                                            "image": "white-bread.jpg"
                                                        },
                                                        {
                                                            "id": 20444,
                                                            "name": "rice",
                                                            "localizedName": "rice",
                                                            "image": "uncooked-white-rice.png"
                                                        },
                                                        {
                                                            "id": 2047,
                                                            "name": "salt",
                                                            "localizedName": "salt",
                                                            "image": "salt.jpg"
                                                        }
                                                    ],
                                                    "equipment": [
                                                        {
                                                            "id": 404645,
                                                            "name": "frying pan",
                                                            "localizedName": "frying pan",
                                                            "image": "pan.png"
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    ],
                                    "originalId": null,
                                    "spoonacularSourceUrl": "https://spoonacular.com/cauliflower-brown-rice-and-vegetable-fried-rice-716426"
                                }
                                """.trim())));

        String getSecondRecipe = "/642129" +
                "/information?" +
                "includeNutrition=false&" +
                "apiKey=" + API_KEY;
        wireMockServer.stubFor(WireMock.get(getSecondRecipe)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                {
                                    "vegetarian": false,
                                    "vegan": false,
                                    "glutenFree": true,
                                    "dairyFree": true,
                                    "veryHealthy": true,
                                    "cheap": false,
                                    "veryPopular": false,
                                    "sustainable": false,
                                    "lowFodmap": false,
                                    "weightWatcherSmartPoints": 3,
                                    "gaps": "no",
                                    "preparationMinutes": -1,
                                    "cookingMinutes": -1,
                                    "aggregateLikes": 22,
                                    "healthScore": 100,
                                    "creditsText": "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
                                    "license": "CC BY 3.0",
                                    "sourceName": "Foodista",
                                    "pricePerServing": 377.64,
                                    "extendedIngredients": [
                                        {
                                            "id": 2044,
                                            "aisle": "Produce",
                                            "image": "fresh-basil.jpg",
                                            "consistency": "SOLID",
                                            "name": "you can use regular basil",
                                            "nameClean": "fresh basil",
                                            "original": "1/2 cup Thai basil or you can use regular basil",
                                            "originalName": "Thai basil or you can use regular basil",
                                            "amount": 0.5,
                                            "unit": "cup",
                                            "meta": [
                                                "canned"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.5,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 12.0,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 2044,
                                            "aisle": "Produce",
                                            "image": "basil.jpg",
                                            "consistency": "SOLID",
                                            "name": "you can use regular basil",
                                            "nameClean": "fresh basil",
                                            "original": "1/2 cup Thai basil or you can use regular basil",
                                            "originalName": "Thai basil or you can use regular basil",
                                            "amount": 0.5,
                                            "unit": "cup",
                                            "meta": [
                                                "canned"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.5,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 12.0,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11250,
                                            "aisle": "Produce",
                                            "image": "Butter-or-Boston-Bibb-lettuce.jpg",
                                            "consistency": "SOLID",
                                            "name": "boston lettuce",
                                            "nameClean": "butter lettuce",
                                            "original": "small head of Boston lettuce, roughly chopped",
                                            "originalName": "Boston lettuce, roughly chopped",
                                            "amount": 1.0,
                                            "unit": "small head",
                                            "meta": [
                                                "roughly chopped"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "small head",
                                                    "unitLong": "small head"
                                                },
                                                "metric": {
                                                    "amount": 1.0,
                                                    "unitShort": "small head",
                                                    "unitLong": "small head"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11819,
                                            "aisle": "Produce",
                                            "image": "red-chili.jpg",
                                            "consistency": "SOLID",
                                            "name": "chili pepper",
                                            "nameClean": "chili pepper",
                                            "original": "small chili pepper, finely chopped",
                                            "originalName": "chili pepper, finely chopped",
                                            "amount": 1.0,
                                            "unit": "small",
                                            "meta": [
                                                "finely chopped"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "small",
                                                    "unitLong": "small"
                                                },
                                                "metric": {
                                                    "amount": 1.0,
                                                    "unitShort": "small",
                                                    "unitLong": "small"
                                                }
                                            }
                                        },
                                        {
                                            "id": 6179,
                                            "aisle": "Ethnic Foods",
                                            "image": "asian-fish-sauce.jpg",
                                            "consistency": "LIQUID",
                                            "name": "fish sauce",
                                            "nameClean": "fish sauce",
                                            "original": "1/4 cup fish sauce",
                                            "originalName": "fish sauce",
                                            "amount": 0.25,
                                            "unit": "cup",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.25,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 58.0,
                                                    "unitShort": "ml",
                                                    "unitLong": "milliliters"
                                                }
                                            }
                                        },
                                        {
                                            "id": 2012,
                                            "aisle": "Spices and Seasonings",
                                            "image": "ground-coriander.jpg",
                                            "consistency": "SOLID",
                                            "name": "coriander leaves",
                                            "nameClean": "dried cilantro",
                                            "original": "1/2 cup coriander (cilantro) leaves",
                                            "originalName": "coriander (cilantro) leaves",
                                            "amount": 0.5,
                                            "unit": "cup",
                                            "meta": [
                                                "(cilantro)"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.5,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 118.294,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11215,
                                            "aisle": "Produce",
                                            "image": "garlic.png",
                                            "consistency": "SOLID",
                                            "name": "garlic",
                                            "nameClean": "garlic",
                                            "original": "1 clove garlic",
                                            "originalName": "garlic",
                                            "amount": 1.0,
                                            "unit": "clove",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "clove",
                                                    "unitLong": "clove"
                                                },
                                                "metric": {
                                                    "amount": 1.0,
                                                    "unitShort": "clove",
                                                    "unitLong": "clove"
                                                }
                                            }
                                        },
                                        {
                                            "id": 9160,
                                            "aisle": "Produce",
                                            "image": "lime-juice.png",
                                            "consistency": "LIQUID",
                                            "name": "lime juice",
                                            "nameClean": "lime juice",
                                            "original": "2 tablespoons fresh lime juice",
                                            "originalName": "fresh lime juice",
                                            "amount": 2.0,
                                            "unit": "tablespoons",
                                            "meta": [
                                                "fresh"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 2.0,
                                                    "unitShort": "Tbsps",
                                                    "unitLong": "Tbsps"
                                                },
                                                "metric": {
                                                    "amount": 2.0,
                                                    "unitShort": "Tbsps",
                                                    "unitLong": "Tbsps"
                                                }
                                            }
                                        },
                                        {
                                            "id": 2064,
                                            "aisle": "Spices and Seasonings",
                                            "image": "mint.jpg",
                                            "consistency": "SOLID",
                                            "name": "mint leaves",
                                            "nameClean": "mint",
                                            "original": "1/2 cup mint leaves",
                                            "originalName": "mint leaves",
                                            "amount": 0.5,
                                            "unit": "cup",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.5,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 22.5,
                                                    "unitShort": "g",
                                                    "unitLong": "grams"
                                                }
                                            }
                                        },
                                        {
                                            "id": 11821,
                                            "aisle": "Produce",
                                            "image": "red-pepper.jpg",
                                            "consistency": "SOLID",
                                            "name": "bell pepper",
                                            "nameClean": "red pepper",
                                            "original": "red bell pepper, thinly sliced",
                                            "originalName": "red bell pepper, thinly sliced",
                                            "amount": 4.0,
                                            "unit": "servings",
                                            "meta": [
                                                "red",
                                                "thinly sliced"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 4.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                },
                                                "metric": {
                                                    "amount": 4.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                }
                                            }
                                        },
                                        {
                                            "id": 1022053,
                                            "aisle": "Ethnic Foods",
                                            "image": "rice-vinegar.png",
                                            "consistency": "LIQUID",
                                            "name": "rice vinegar",
                                            "nameClean": "rice vinegar",
                                            "original": "1/4 cup rice vinegar",
                                            "originalName": "rice vinegar",
                                            "amount": 0.25,
                                            "unit": "cup",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.25,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 63.75,
                                                    "unitShort": "ml",
                                                    "unitLong": "milliliters"
                                                }
                                            }
                                        },
                                        {
                                            "id": 15270,
                                            "aisle": "Seafood",
                                            "image": "shrimp.png",
                                            "consistency": "SOLID",
                                            "name": "shrimp",
                                            "nameClean": "shrimp",
                                            "original": "8 large cooked shrimp, slice in half lengthways",
                                            "originalName": "cooked shrimp, slice in half lengthways",
                                            "amount": 8.0,
                                            "unit": "large",
                                            "meta": [
                                                "cooked"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 8.0,
                                                    "unitShort": "large",
                                                    "unitLong": "larges"
                                                },
                                                "metric": {
                                                    "amount": 8.0,
                                                    "unitShort": "large",
                                                    "unitLong": "larges"
                                                }
                                            }
                                        },
                                        {
                                            "id": 10118368,
                                            "aisle": "Ethnic Foods",
                                            "image": "rice-paper.jpg",
                                            "consistency": "SOLID",
                                            "name": "spring roll wrappers",
                                            "nameClean": "spring roll wrappers",
                                            "original": "8 large (8-inch) spring roll wrappers",
                                            "originalName": "(8-inch) spring roll wrappers",
                                            "amount": 8.0,
                                            "unit": "8-inch",
                                            "meta": [
                                                "()"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 8.0,
                                                    "unitShort": "8-inch",
                                                    "unitLong": "8-inchs"
                                                },
                                                "metric": {
                                                    "amount": 8.0,
                                                    "unitShort": "8-inch",
                                                    "unitLong": "8-inchs"
                                                }
                                            }
                                        },
                                        {
                                            "id": 19335,
                                            "aisle": "Baking",
                                            "image": "sugar-in-bowl.png",
                                            "consistency": "SOLID",
                                            "name": "sugar",
                                            "nameClean": "sugar",
                                            "original": "1 tablespoon sugar",
                                            "originalName": "sugar",
                                            "amount": 1.0,
                                            "unit": "tablespoon",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 1.0,
                                                    "unitShort": "Tbsp",
                                                    "unitLong": "Tbsp"
                                                },
                                                "metric": {
                                                    "amount": 1.0,
                                                    "unitShort": "Tbsp",
                                                    "unitLong": "Tbsp"
                                                }
                                            }
                                        },
                                        {
                                            "id": 14412,
                                            "aisle": "Beverages",
                                            "image": "water.png",
                                            "consistency": "LIQUID",
                                            "name": "water",
                                            "nameClean": "water",
                                            "original": "1/4 cup water",
                                            "originalName": "water",
                                            "amount": 0.25,
                                            "unit": "cup",
                                            "meta": [],
                                            "measures": {
                                                "us": {
                                                    "amount": 0.25,
                                                    "unitShort": "cups",
                                                    "unitLong": "cups"
                                                },
                                                "metric": {
                                                    "amount": 59.147,
                                                    "unitShort": "ml",
                                                    "unitLong": "milliliters"
                                                }
                                            }
                                        },
                                        {
                                            "id": -1,
                                            "aisle": "?",
                                            "image": null,
                                            "consistency": "SOLID",
                                            "name": "nuoc cham",
                                            "nameClean": null,
                                            "original": "Nuoc Cham (dipping sauce)",
                                            "originalName": "Nuoc Cham (dipping sauce)",
                                            "amount": 4.0,
                                            "unit": "servings",
                                            "meta": [
                                                "(dipping sauce)"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 4.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                },
                                                "metric": {
                                                    "amount": 4.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                }
                                            }
                                        },
                                        {
                                            "id": -1,
                                            "aisle": "?",
                                            "image": null,
                                            "consistency": "SOLID",
                                            "name": "nuoc cham",
                                            "nameClean": null,
                                            "original": "Nuoc Cham (dipping sauce)",
                                            "originalName": "Nuoc Cham (dipping sauce)",
                                            "amount": 4.0,
                                            "unit": "servings",
                                            "meta": [
                                                "(dipping sauce)"
                                            ],
                                            "measures": {
                                                "us": {
                                                    "amount": 4.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                },
                                                "metric": {
                                                    "amount": 4.0,
                                                    "unitShort": "servings",
                                                    "unitLong": "servings"
                                                }
                                            }
                                        }
                                    ],
                                    "id": 642129,
                                    "title": "Easy To Make Spring Rolls",
                                    "readyInMinutes": 45,
                                    "servings": 4,
                                    "sourceUrl": "http://www.foodista.com/recipe/B5HHJWNP/easy-to-make-spring-rolls",
                                    "image": "https://spoonacular.com/recipeImages/642129-556x370.jpg",
                                    "imageType": "jpg",
                                    "summary": "If you want to add more <b>gluten free, dairy free, and pescatarian</b> recipes to your collection, Easy To Make Spring Rolls might be a recipe you should try. For <b>$3.78 per serving</b>, you get a hor d'oeuvre that serves 4. One serving contains <b>162 calories</b>, <b>13g of protein</b>, and <b>2g of fat</b>. 22 people found this recipe to be flavorful and satisfying. From preparation to the plate, this recipe takes about <b>45 minutes</b>. It can be enjoyed any time, but it is especially good for <b>Spring</b>. This recipe from Foodista requires mint leaves, garlic, chili pepper, and rice vinegar. This recipe is typical of Vietnamese cuisine. Overall, this recipe earns a <b>tremendous spoonacular score of 88%</b>. <a href=\\"https://spoonacular.com/recipes/easy-to-make-spring-rolls-1262689\\">Easy To Make Spring Rolls</a>, <a href=\\"https://spoonacular.com/recipes/easy-to-make-spring-rolls-1218889\\">Easy To Make Spring Rolls</a>, and <a href=\\"https://spoonacular.com/recipes/easy-to-make-spring-rolls-1531113\\">Easy To Make Spring Rolls</a> are very similar to this recipe.",
                                    "cuisines": [
                                        "Vietnamese",
                                        "Asian"
                                    ],
                                    "dishTypes": [
                                        "fingerfood",
                                        "antipasti",
                                        "starter",
                                        "snack",
                                        "appetizer",
                                        "antipasto",
                                        "hor d'oeuvre"
                                    ],
                                    "diets": [
                                        "gluten free",
                                        "dairy free",
                                        "pescatarian"
                                    ],
                                    "occasions": [
                                        "spring",
                                        "easter"
                                    ],
                                    "winePairing": {
                                        "pairedWines": [
                                            "sparkling wine",
                                            "sparkling rose"
                                        ],
                                        "pairingText": "Sparkling Wine and Sparkling rosé are my top picks for Spring Rolls. If you're serving a selection of appetizers, you can't go wrong with these. Both are very food friendly and complement a variety of flavors. You could try Billecart-Salmon Blanc de Blancs Grand Cru. Reviewers quite like it with a 4.7 out of 5 star rating and a price of about 80 dollars per bottle.",
                                        "productMatches": [
                                            {
                                                "id": 436961,
                                                "title": "Billecart-Salmon Blanc de Blancs Grand Cru",
                                                "description": "The fine mousse of delicate bubbles elegantly accompanies the glints of its gold color. A cuvée that will surprise you with its pure intensity of dry fruits aromas, almonds and fresh hazelnuts mixed with those of fruits of white flesh. The creamy sensation of the bubble associated to aromas of buttered brioche and mineral aromas. Persistent finish with a fresh and elegant sweetness.This great bottle will make a perfect marriage with caviar or fine oysters but also with grilled fish and seafood.",
                                                "price": "$79.98999786376953",
                                                "imageUrl": "https://spoonacular.com/productImages/436961-312x231.jpg",
                                                "averageRating": 0.9399999976158142,
                                                "ratingCount": 5.0,
                                                "score": 0.8774999976158142,
                                                "link": "https://click.linksynergy.com/deeplink?id=*QCiIS6t4gA&mid=2025&murl=https%3A%2F%2Fwww.wine.com%2Fproduct%2Fbillecart-salmon-blanc-de-blancs-grand-cru%2F25061"
                                            }
                                        ]
                                    },
                                    "instructions": "<ol><li>Have all the ingredients ready for assembly. In a large bowl filled with water, dip a wrapper in the water. The rice wrapper will begin to soften and this is your cue to remove it from the water and lay it flat. Place 2 shrimp halves in a row across the center and top with basil, mint, cilantro and lettuce. Leave about 1 to 2 inches uncovered on each side.  Fold uncovered sides inward, then tightly roll the wrapper, beginning at the end with the lettuce.  Repeat with remaining wrappers and ingredients. Cut and serve at room temperature with dipping sauce.</li><li>The Culinary Chases Note: The rice wrapper can be fussy to handle if you let it soak too long. I usually give it a couple of swishes in the water and then remove. It may feel slightly stiff but by the time you are ready to roll up, the wrapper will become very pliable.  A typical spring roll contains cooked rice vermicelli, slivers of cooked pork and julienned carrots but you can use whatever suits your fancy.  Enjoy!</li></ol>",
                                    "analyzedInstructions": [
                                        {
                                            "name": "",
                                            "steps": [
                                                {
                                                    "number": 1,
                                                    "step": "Have all the ingredients ready for assembly. In a large bowl filled with water, dip a wrapper in the water. The rice wrapper will begin to soften and this is your cue to remove it from the water and lay it flat.",
                                                    "ingredients": [
                                                        {
                                                            "id": 14412,
                                                            "name": "water",
                                                            "localizedName": "water",
                                                            "image": "water.png"
                                                        },
                                                        {
                                                            "id": 20444,
                                                            "name": "rice",
                                                            "localizedName": "rice",
                                                            "image": "uncooked-white-rice.png"
                                                        },
                                                        {
                                                            "id": 0,
                                                            "name": "dip",
                                                            "localizedName": "dip",
                                                            "image": ""
                                                        }
                                                    ],
                                                    "equipment": [
                                                        {
                                                            "id": 404783,
                                                            "name": "bowl",
                                                            "localizedName": "bowl",
                                                            "image": "bowl.jpg"
                                                        }
                                                    ]
                                                },
                                                {
                                                    "number": 2,
                                                    "step": "Place 2 shrimp halves in a row across the center and top with basil, mint, cilantro and lettuce. Leave about 1 to 2 inches uncovered on each side.  Fold uncovered sides inward, then tightly roll the wrapper, beginning at the end with the lettuce.  Repeat with remaining wrappers and ingredients.",
                                                    "ingredients": [
                                                        {
                                                            "id": 11165,
                                                            "name": "cilantro",
                                                            "localizedName": "cilantro",
                                                            "image": "cilantro.png"
                                                        },
                                                        {
                                                            "id": 11252,
                                                            "name": "lettuce",
                                                            "localizedName": "lettuce",
                                                            "image": "iceberg-lettuce.jpg"
                                                        },
                                                        {
                                                            "id": 15270,
                                                            "name": "shrimp",
                                                            "localizedName": "shrimp",
                                                            "image": "shrimp.png"
                                                        },
                                                        {
                                                            "id": 2044,
                                                            "name": "basil",
                                                            "localizedName": "basil",
                                                            "image": "basil.jpg"
                                                        },
                                                        {
                                                            "id": 2064,
                                                            "name": "mint",
                                                            "localizedName": "mint",
                                                            "image": "mint.jpg"
                                                        },
                                                        {
                                                            "id": 0,
                                                            "name": "roll",
                                                            "localizedName": "roll",
                                                            "image": "dinner-yeast-rolls.jpg"
                                                        }
                                                    ],
                                                    "equipment": []
                                                },
                                                {
                                                    "number": 3,
                                                    "step": "Cut and serve at room temperature with dipping sauce.The Culinary Chases Note: The rice wrapper can be fussy to handle if you let it soak too long. I usually give it a couple of swishes in the water and then remove. It may feel slightly stiff but by the time you are ready to roll up, the wrapper will become very pliable.  A typical spring roll contains cooked rice vermicelli, slivers of cooked pork and julienned carrots but you can use whatever suits your fancy.  Enjoy!",
                                                    "ingredients": [
                                                        {
                                                            "id": 20134,
                                                            "name": "cooked rice vermicelli",
                                                            "localizedName": "cooked rice vermicelli",
                                                            "image": "rice-noodles.jpg"
                                                        },
                                                        {
                                                            "id": 0,
                                                            "name": "dipping sauce",
                                                            "localizedName": "dipping sauce",
                                                            "image": ""
                                                        },
                                                        {
                                                            "id": 10073,
                                                            "name": "cooked pork",
                                                            "localizedName": "cooked pork",
                                                            "image": "pulled-pork.png"
                                                        },
                                                        {
                                                            "id": 11124,
                                                            "name": "carrot",
                                                            "localizedName": "carrot",
                                                            "image": "sliced-carrot.png"
                                                        },
                                                        {
                                                            "id": 14412,
                                                            "name": "water",
                                                            "localizedName": "water",
                                                            "image": "water.png"
                                                        },
                                                        {
                                                            "id": 20444,
                                                            "name": "rice",
                                                            "localizedName": "rice",
                                                            "image": "uncooked-white-rice.png"
                                                        },
                                                        {
                                                            "id": 0,
                                                            "name": "roll",
                                                            "localizedName": "roll",
                                                            "image": "dinner-yeast-rolls.jpg"
                                                        }
                                                    ],
                                                    "equipment": []
                                                }
                                            ]
                                        }
                                    ],
                                    "originalId": null,
                                    "spoonacularSourceUrl": "https://spoonacular.com/easy-to-make-spring-rolls-642129"
                                }
                                """.trim())));
        //when
        catalogInitializerService.fetchData(0, 2);

        //then
        assertThat(repository.findAll()).hasSize(2);
    }
}