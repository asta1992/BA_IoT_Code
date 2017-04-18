package ch.hsr.smartmanager.service.http;

import ch.hsr.smartmanager.service.LoginMethod;

public enum HttpLoginMethod implements LoginMethod{
	BASIC_AUTH, DIGEST_AUTH, CLIENT_CERT, NONE
}
