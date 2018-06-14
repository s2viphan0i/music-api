package com.sinnguyen.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
	@RequestMapping(value="/delete", method=RequestMethod.POST)
    public Object delete(@RequestBody String json) {
        JSONObject jsonData = new JSONObject(json);
        if(jsonData.getString("document_id") == null || jsonData.getString("document_id").equals("")) {
            return new ResponseEntity<String>(JsonObject.create().put("message", "A document id is required").toString(), HttpStatus.BAD_REQUEST);
        }
        return Database.delete(bucket(), jsonData.getString("document_id"));
    }
}
