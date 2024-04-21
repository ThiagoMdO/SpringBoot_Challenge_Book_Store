package com.thiagomdo.ba.challenge.msfeedback.resources;

import com.thiagomdo.ba.challenge.msfeedback.model.dto.FeedbackDTO;
import com.thiagomdo.ba.challenge.msfeedback.model.request.FeedbackRequest;
import com.thiagomdo.ba.challenge.msfeedback.services.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedBackResource {

    @Autowired
    FeedbackService feedBackService;

    @Operation(
            operationId = "getAllFeedBack",
            summary = "Returns all feedbacks.",
            description = "Returns a list of all available feedbacks.",
            tags = {"Feedback"},
            responses = {
                @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackDTO1.class)))
                }),
                @ApiResponse(responseCode = "x-200", description = "OK", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.EmptyListException.class)))
                }),
                @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class)))
                })
            }
    )
    @GetMapping
    public ResponseEntity<List<FeedbackDTO>> getAllFeedBack() {
        List<FeedbackDTO> listFeedBack = feedBackService.getAll();
        return ResponseEntity.ok().body(listFeedBack);
    }


    @Operation(
            operationId = "getFeedBackById",
            summary = "Return feedback by Id.",
            description = "Returns a specific feedback by Id.",
            tags = {"Feedback by Id"},
            parameters = {
              @Parameter(name = "id", description = "Search feedback by Id", required = true, example = "6605903e1e2d5c55c2017111")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackDTO1.class))
                }),
                @ApiResponse(responseCode = "404", description = "Not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackNotFoundException.class))
                }),
                @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
                })
            }
    )
//    @Parameter(name = "id", description = "Search feedback by Id", required = true, example = "6605903e1e2d5c55c2017111")
    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getFeedBackById(@PathVariable String id) {
        FeedbackDTO feedBackDTO = feedBackService.getById(id);
        return ResponseEntity.ok().body(feedBackDTO);
    }

    @Operation(
        operationId = "createFeedBack",
        summary = "Create Feedback",
        description = "It's possible to create a new feedback",
        tags = {"Feedback"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Create Feedback Request",
            required = true,
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackRequest.class)
            )
        ),
        responses = {
                @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackDTO1.class))
                }),
                @ApiResponse(responseCode = "404", description = "Not Found", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
                }),
                @ApiResponse(responseCode = "400", description = "NotPossibleToCommentOrderException", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.NotPossibleToCommentOrderException.class))
                }),
                @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
                })
        }
    )
    @PostMapping
    public ResponseEntity<FeedbackDTO> createFeedBack(@RequestBody FeedbackRequest request) {
        FeedbackDTO feedBackDTO = feedBackService.create(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(feedBackDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(feedBackDTO);
    }

    @Operation(
        operationId = "updateFeedBack",
        summary = "Update a Feedback",
        description = "Update a specific feedback by Id.",
        tags = {"Feedback by Id"},
        parameters = {
            @Parameter(name = "id", description = "Search feedback by Id", required = true, example = "6605903e1e2d5c55c2017111")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Update Feedback Request",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackRequest.class)
                )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackDTO1.class))
            }),
            @ApiResponse(responseCode = "404" , description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackNotFoundException.class))
            }),
            @ApiResponse(responseCode = "x-404", description = "OrderNotFoundException", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.OrderNotFoundException.class))
            }),
            @ApiResponse(responseCode = "400", description = "FeedbackNotFoundException", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackNotFoundException.class))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            })
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<FeedbackDTO> updateFeedBack(@PathVariable String id, @RequestBody FeedbackRequest request) {
        FeedbackDTO feedBackDTO = feedBackService.update(id, request);

        return ResponseEntity.ok().body(feedBackDTO);
    }

    @Operation(
        operationId = "deleteFeedBack",
        summary = "Delete a Feedback",
        description = "Delete a specific feedback by Id.",
        tags = {"Feedback by Id"},
        parameters = {
                @Parameter(name = "id", description = "Search feedback by Id", required = true, example = "6605903e1e2d5c55c2017111")
        },
        responses = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404" , description = "Not Found", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.FeedbackNotFoundException.class))
            }),
            @ApiResponse(responseCode = "500", description = "SERVICE_UNAVAILABLE", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = com.devertelo.springswaggercodegen3.model.SERVICEUNAVAILABLE.class))
            })
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable String id) {
        feedBackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
