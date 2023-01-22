package com.ie.dronesmanagement.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Error {

	private int code;
	private String reason;
	private LocalDateTime timestamp;
}
