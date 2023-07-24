package com.RestAssuredReqres;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

@SuppressWarnings("unchecked")
public class HTTPRequest 
{
	String url = "https://reqres.in/";
	String endPoint = "api/users/";
	int id;
	
	@Test(priority=1, dependsOnMethods= {"postMethod"})
	public void getResources()
    {
		given().
			baseUri(url).basePath(endPoint).
		when().
			get("?page=2").
		then().
			assertThat().statusCode(200).log().all();
    }
	
	@Test(priority=2)
	public void postMethod()
	{
		// JSONObject is a class that represents a Simple JSON.
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("name", "Gorav"); 
		requestParams.put("profile", "Automation engg.");
		
//		First Way:- 
//		given().
//			baseUri(url).basePath(endPoint).header("Content-Type", "application/json").
//			contentType(ContentType.JSON).body(requestParams.toString()).
//		when().
//			post().
//		then().
//			assertThat().statusCode(201).log().all();
		
//		Second way:-
		// here we store a request response in a response variable.
		Response response =
				given().baseUri(url).basePath(endPoint).
					header("Content-Type", "application/json").contentType(ContentType.JSON).
					body(requestParams.toString()).
				when().
					post();
		
		// here we assert the request
		System.out.println("Assertion:-");
		response.then().assertThat().statusCode(201).log().all();
		
		// here we get the resource id
		id = response.jsonPath().getInt("id");
		System.out.print("ID: "+id);
	}
	
	@Test(priority=3, dependsOnMethods= {"postMethod"})
	public void patchMethod()
	{
		// JSONObject is a class that represents a Simple JSON.
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("name", "Gaurav");
			
		given().
			baseUri(url).basePath(endPoint).header("Content-Type", "application/json").
			contentType(ContentType.JSON).body(requestParams.toString()).
		when().
			patch("{id}",id).
		then().
			assertThat().statusCode(200).log().all();
	}
	
	@Test(priority=4, dependsOnMethods= {"postMethod"})
	public void putMethod()
	{
		// JSONObject is a class that represents a Simple JSON.
		JSONObject requestParams = new JSONObject(); 
		requestParams.put("name", "Gaurav Mahor"); 
		requestParams.put("profile", "Automation Tester");
			
		given().
			baseUri(url).basePath(endPoint).header("Content-Type", "application/json").
			contentType(ContentType.JSON).body(requestParams.toString()).
		when().
			put("{id}",id).
		then().
			assertThat().statusCode(200).log().all();
	}
}
