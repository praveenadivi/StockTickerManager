package com.rbc.stocktickermanager.controller;

import com.rbc.stocktickermanager.entity.Trade;
import com.rbc.stocktickermanager.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


@RestController
@RestControllerAdvice
public class TradeController {
    @Autowired
    TradeService tradeService;

    @Operation(summary = "Get all saved trades")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all trades in the database",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Trade.class)) })})
    @GetMapping("/findAll")
    @ResponseBody
    public List<Trade> findAll() {
        return tradeService.findAll();
    }

    @Operation(summary = "API for RFBs etc to check if the service is still up")
    @GetMapping("/")
    public String index() {
        return "successfully loaded the TradeController";
    }

    @Operation(summary = "API for reporting a single trade")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the saved object with an " +
                    "unique identifier that can be used to search for the trade again", content = {
                    @Content(schema =@Schema(implementation = Trade.class))
            }),
            @ApiResponse(responseCode = "400", description = "No trade was provided"),
            @ApiResponse(responseCode = "406", description = "The trade could not be saved")
    })
    @ResponseBody
    @PostMapping("/report")
    public ResponseEntity<Object> reportATrade(@RequestBody Trade trade) {
        if (trade == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("the trade data is invalid (CODE 400)\n");

        }
        Optional<Trade> tradeData = tradeService.reportATrade(trade);
        if (tradeData.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tradeService.reportATrade(tradeData.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body("Could not save the trade");
        }

    }

    @Operation(summary = "Load all trades in the file (provide full file path). " +
            "It will check for duplicates using a MD5 hash on the " +
            "file data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The upload succeeded"),
            @ApiResponse(responseCode = "406", description = "The file name provided was found to be empty or invalid."),
            @ApiResponse(responseCode = "400", description = "The a duplicate file with the same content was found " +
                    "or the data has issues and failed during the upload")          ,
            @ApiResponse(responseCode = "422", description = "The file provided could not be found " +
                    "to an un expected exception")
    })
    @ResponseBody
    @PostMapping("/loadTrades")
    public ResponseEntity<Object> loadTrades( @Parameter(name = "filename", description = "Full file path")
                                                  @RequestParam("fileName") String fileName) {
        if (StringUtils.isEmpty(fileName) || Files.notExists(Path.of(fileName))) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("FileName provided is empty or not found");
        }

        try {
            Optional<List<Trade>> data = tradeService.bulkUploadTickerInfo(fileName);

            if (!data.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Did not save the data either because " +
                        "the same was already uploaded or the data is incorrect");
            }

            return ResponseEntity.status(HttpStatus.OK).body(data.get());
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The file provided could not be loaded");
        }
    }


    @Operation( summary= "Get all the trades basing on a ticker symbol")
    @GetMapping("/ticker/{tickerSymbol}")
    @ResponseBody
    public ResponseEntity<Object> findATradesByTickerSymbol(
            @Parameter(name = "tickerSymbol", description = "Ticker symbol to search for")
            @PathVariable("tickerSymbol") String tickerSymbol) {
        if (StringUtils.isEmpty(tickerSymbol)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ticker symbol provided");
        }


        Optional<List<Trade>> trades = tradeService.findATradeByTickerSymbol(tickerSymbol);

        if (trades.isPresent()) {

            return ResponseEntity.status(HttpStatus.OK).body(trades.get());
        } else {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No trades were found " +
                    "for the ticker symbol" + tickerSymbol);
        }

    }
}
