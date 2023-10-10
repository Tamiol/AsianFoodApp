package com.example.asianfoodapp.catalog.fasada;

import com.example.asianfoodapp.auth.repository.UserRepository;
import com.example.asianfoodapp.catalog.domain.Recipe;
import com.example.asianfoodapp.catalog.domain.dto.RestIngredientDTO;
import com.example.asianfoodapp.catalog.domain.dto.RestRecipeDTO;
import com.example.asianfoodapp.catalog.repository.RecipeRepository;
import com.example.asianfoodapp.catalog.services.port.CatalogUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class CatalogAddRecipeTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RecipeRepository recipeRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CatalogUseCase catalog;

    @Test
    @WithMockUser(username = "user", password ="12345678", roles = "USER")
    void loggedUserAddRecipe() throws Exception {
        //given
        var ingredientDTO = new RestIngredientDTO("egg", 3d, "");
        var recipeDTO = new RestRecipeDTO("omelette", Set.of(ingredientDTO),40, "description", true, false, false, "www.zdj.pl");
        String requestBody = STR."""
                {
                    "name":"\{recipeDTO.name()}",
                    "ingredients":
                        [
                            {
                            "name":"\{ingredientDTO.name()}",
                            "amount":\{ingredientDTO.amount()},
                            "unit":"\{ingredientDTO.unit()}"
                            }
                        ],
                    "readyInMinutes":\{recipeDTO.readyInMinutes()},
                    "instructions": "\{recipeDTO.instructions()}",
                    "vegetarian": \{recipeDTO.vegetarian()},
                    "vegan": \{recipeDTO.vegan()},
                    "glutenFree": \{recipeDTO.glutenFree()},
                    "image": "\{recipeDTO.image()}"
                }
                """;
        var recipeResponse = new Recipe();
        recipeResponse.setId(1L);

        //when
        Mockito.when(catalog.addRecipe(recipeDTO.toCreateCommand(), "user"))
                .thenReturn(Optional.of(recipeResponse));

        //then
        mockMvc.perform(post("/catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/catalog/1")));
    }

    @Test
    void notLoggedUserCannotAddRecipe() throws Exception {
        //given
        var ingredientDTO = new RestIngredientDTO("egg", 3d, "");
        var recipeDTO = new RestRecipeDTO("omelette", Set.of(ingredientDTO),40, "description", true, false, false, "www.zdj.pl");
        String requestBody = STR."""
                {
                    "name":"\{recipeDTO.name()}",
                    "ingredients":
                        [
                            {
                            "name":"\{ingredientDTO.name()}",
                            "amount":\{ingredientDTO.amount()},
                            "unit":"\{ingredientDTO.unit()}"
                            }
                        ],
                    "readyInMinutes":\{recipeDTO.readyInMinutes()},
                    "instructions": "\{recipeDTO.instructions()}",
                    "vegetarian": \{recipeDTO.vegetarian()},
                    "vegan": \{recipeDTO.vegan()},
                    "glutenFree": \{recipeDTO.glutenFree()},
                    "image": "\{recipeDTO.image()}"
                }
                """;
        var recipeResponse = new Recipe();
        recipeResponse.setId(1L);

        //when

        //then
        mockMvc.perform(post("/catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", password ="12345678", roles = "USER")
    void recipeWithExistingNameCannotBeAdded() throws Exception {
        //given
        var ingredientDTO = new RestIngredientDTO("egg", 3d, "");
        var recipeDTO = new RestRecipeDTO("omelette", Set.of(ingredientDTO),40, "description", true, false, false, "www.zdj.pl");
        String requestBody = STR."""
                {
                    "name":"\{recipeDTO.name()}",
                    "ingredients":
                        [
                            {
                            "name":"\{ingredientDTO.name()}",
                            "amount":\{ingredientDTO.amount()},
                            "unit":"\{ingredientDTO.unit()}"
                            }
                        ],
                    "readyInMinutes":\{recipeDTO.readyInMinutes()},
                    "instructions": "\{recipeDTO.instructions()}",
                    "vegetarian": \{recipeDTO.vegetarian()},
                    "vegan": \{recipeDTO.vegan()},
                    "glutenFree": \{recipeDTO.glutenFree()},
                    "image": "\{recipeDTO.image()}"
                }
                """;

        //when
        Mockito.when(catalog.addRecipe(recipeDTO.toCreateCommand(), "user"))
                .thenReturn(Optional.empty());

        //then
        mockMvc.perform(post("/catalog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                )
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"Recipe with provided name: omelette already exist\"", result.getResolvedException().getMessage()));
    }
}
