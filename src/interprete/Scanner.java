/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interprete;

/**
 *
 * @author Sanch
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.util.regex.Pattern;

public class Scanner {

    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;

    private static final Map<String, Tipo_Token> palabrasReservadas;
    private static final Map<String, Tipo_Token> TokensConLexema;
    StringBuilder buffer;
    
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", Tipo_Token.Y);
        palabrasReservadas.put("clase", Tipo_Token.CLASE);
        palabrasReservadas.put("ademas", Tipo_Token.ADEMAS);
        palabrasReservadas.put("si",Tipo_Token.SI);
        palabrasReservadas.put("nulo",Tipo_Token.NULO); 
        palabrasReservadas.put("o", Tipo_Token.O);
        palabrasReservadas.put("imprimir", Tipo_Token.IMPRIMIR);
        palabrasReservadas.put("retornar", Tipo_Token.DEVOLVER);
        palabrasReservadas.put("falso", Tipo_Token.FALSO);
        palabrasReservadas.put("verdadero", Tipo_Token.VERDADERO);
        palabrasReservadas.put("mientras", Tipo_Token.MIENTRAS);
        palabrasReservadas.put("para", Tipo_Token.PARA);
        palabrasReservadas.put("funcion", Tipo_Token.FUNCION);
        palabrasReservadas.put("super", Tipo_Token.SUPER);
        palabrasReservadas.put("este", Tipo_Token.ESTE);
        palabrasReservadas.put("variable", Tipo_Token.VARIABLE);
        
        TokensConLexema = new HashMap<>();
        TokensConLexema.put("<", Tipo_Token.MENOR);
        TokensConLexema.put("<=", Tipo_Token.MENOR_IGUAL);
        TokensConLexema.put(">", Tipo_Token.MAYOR);
        TokensConLexema.put(">=", Tipo_Token.MAYOR_IGUAL);
        TokensConLexema.put("==", Tipo_Token.IGUAL);
        TokensConLexema.put("!=", Tipo_Token.DIFERENTE_DE);
        TokensConLexema.put("!", Tipo_Token.NO);
        TokensConLexema.put("=", Tipo_Token.ASIGNAR);
        TokensConLexema.put("+", Tipo_Token.SUMA);
        TokensConLexema.put("+=", Tipo_Token.MAS_IGUAL);
        TokensConLexema.put("-", Tipo_Token.RESTA);
        TokensConLexema.put("-=", Tipo_Token.MENOS_IGUAL);
        TokensConLexema.put("*", Tipo_Token.MULTIPLICACION);
        TokensConLexema.put("*=", Tipo_Token.POR_IGUAL);
        TokensConLexema.put("/", Tipo_Token.DIVISION);
        TokensConLexema.put("/=", Tipo_Token.DIV_IGUAL);
        TokensConLexema.put("{", Tipo_Token.LLAVE_IZQ);
        TokensConLexema.put("}", Tipo_Token.LLAVE_DER);
        TokensConLexema.put("(", Tipo_Token.PARENTESIS_IZQ);
        TokensConLexema.put(")", Tipo_Token.PARENTESIS_DER);
        TokensConLexema.put("&&", Tipo_Token.Y);
        TokensConLexema.put("||", Tipo_Token.O);
        TokensConLexema.put("&", Tipo_Token.AMPERSAND);
        TokensConLexema.put(",", Tipo_Token.COMA);
        TokensConLexema.put(".", Tipo_Token.PUNTO);
        TokensConLexema.put(";", Tipo_Token.PUNTO_Y_COMA);
        TokensConLexema.put("[", Tipo_Token.CORCHETE_IZQ);
        TokensConLexema.put("]", Tipo_Token.CORCHETE_DER);
    }

    Scanner(String source){
        this.source = source;
        this.buffer = new StringBuilder();
    }
    
    private boolean validarTrans(char caracter, String regex) {
    return Pattern.compile(regex)
        .matcher(String.valueOf(caracter))
        .matches();
    }

    List<Token> scanTokens(){
        int estado = 0;
        for (int i = 0; i <= this.source.length(); i++) {
            char flujo = (i == this.source.length()) ? '\0' : this.source.charAt(i);
            linea = flujo == '\n' ? ++linea : linea;
            switch (estado) {
                
                case 0:
                    // Si existe la tansición, debemos agregar el caracter al buffer.
                    if (validarTrans(flujo, "<")) {
                       estado = 1;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "=")) {
                       estado = 4;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, ">")) {
                       estado = 7;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "!")) {
                       estado = 10;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "\\+")) {
                       estado = 30;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "-")) {
                       estado = 33;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "\\*")) {
                       estado = 35;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "\\/")) {
                       estado = 38;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "//|")) {
                       estado = 40;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "//&")) {
                       estado = 43;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, ".")) {
                       estado = 24;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "\\d")) {
                       estado = 22;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "[a-zA-Z]")) {
                       estado = 26;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "\\\\(|\\\\)|\\\\{|\\\\}|[|]")) {
                       estado = 18;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, ".|\\\\;|\\\\,|\\\\?|\\\\¿|\\\\%|\\\\_")) {
                       estado = 20;
                       buffer.append(flujo);
                    }
                    else if (validarTrans(flujo, "\\s")) {
                       estado = 28;
                       buffer.append(flujo);
                    }
                break;
                
                case 1:
                    if (validarTrans(flujo, "=")) {
                        estado = 2;
                        buffer.append(flujo);
                    }
                    else {
                        estado = 0;
                        i--;
                        AgregarToken(buffer.toString());
                    }
                break;
                
                case 2: case 6: case 8: case 11:
                    estado = 0;
                    i--;
                    AgregarToken(buffer.toString());
                break;
                
                case 4:
                    if (validarTrans(flujo, "=")) {
                        estado = 6;
                        buffer.append(flujo);
                    }
                    else {
                        estado = 0;
                        i--;    
                        AgregarToken(buffer.toString());
                    }
                break;
                
                case 7:
                    if (validarTrans(flujo, "=")) {
                        estado = 8;
                        buffer.append(flujo);
                    }
                     // Estado de aceptación
                    else {
                        estado = 0;
                        i--;
                        AgregarToken(buffer.toString());
                  }
                break;
                
                case 10:
                    if (validarTrans(flujo, "=")) {
                        estado = 11;
                        buffer.append(flujo);
                    }
                    else {
                        estado = 0;
                        i--;
                        AgregarToken(buffer.toString());
                    }
                break;
                
                case 20:
                    if(validarTrans(flujo, "=")){
                        estado = 21;
                        buffer.append(flujo);
                    }
                    else{
                        estado = 0;
                        i--;
                        AgregarToken(buffer.toString());
                    }
                break;
                case 22:
                    if(validarTrans(flujo, "=")){
                        estado=23;
                        buffer.append(flujo);
                    }
                    else{
                        estado=0;
                        i--;
                        AgregaToken(buffer.toString(),Tipo_Token.NUMERO);
                    }
                break;
                case 26:
                    if(validarTrans(flujo, "=")){
                        estado=27;
                        buffer.append(flujo);
                    }
                    else{
                        estado=0;
                        i--;
                        AgregaToken(buffer.toString(),Tipo_Token.LETRA);
                    }
                break;
                case 24: 
                        if(validarTrans(flujo,"=")){
                          estado=25;
                          buffer.append(flujo);
                        }
                        else{
                            estado = 0;
                            i--;
                            AgregaToken(buffer.toString(), Tipo_Token.IDENTIFICADOR);
                        }
                 break;
                case 30:
                        if(validarTrans(flujo, "=")){
                            estado=31;
                            buffer.append(flujo);
                        }
                        else{
                            estado=0;
                            i--;
                            AgregarToken(buffer.toString());
                        }
                break;
                case 33:
                    if(validarTrans(flujo, "=")){
                        estado=34;
                        buffer.append(flujo);
                    }
                    else{
                        estado=0;
                        i--;
                        AgregarToken(buffer.toString());
                    }
                break;
                case 35:
                  if(validarTrans(flujo, "=")){
                    estado=36;
                    buffer.append(flujo);
                }
                else{
                  estado=0;
                  i--;
                  AgregarToken(buffer.toString());
                }
              
                break;
                case 38:
                if(validarTrans(flujo, "=")){
                    estado=39;
                    buffer.append(flujo);
                }
                else{
                  estado=0;
                  i--;
                  AgregarToken(buffer.toString());
                }
                break;
                case 31: case 34: case 36: case 39:
                    if(validarTrans(flujo, "=")){
                        estado = 40;
                        buffer.append(flujo);
                    }
                    else {
                        estado = 0;
                        i--;
                        AgregarToken(buffer.toString());
                    }
                break;
                case 40:
                    if(validarTrans(flujo, "=")){
                        estado = 41;
                        buffer.append(flujo);
                    }
                    else{
                        estado=0;
                        i--;
                        AgregarToken(buffer.toString());
                    }
                break;
                case 43:
                    if(validarTrans(flujo, "=")){
                      estado = 44;
                      buffer.append(flujo);
                    }
                    else{
                      estado=0;
                      i--;
                      AgregarToken(buffer.toString());
                    }
                break;
                case 41: case 44: 
                  if(validarTrans(flujo, "=")){
                    estado = 0;
                    i--;
                    AgregarToken(buffer.toString());
                  }
                break;
            case 28:
              if(validarTrans(flujo, "=")){
                estado=29;
                buffer.append(flujo);
              }
              else{
                estado=0;
                i--;
                AgregaToken(buffer.toString(),Tipo_Token.NUEVAL);
              }
            break;
                    default:
                        estado = 0;
                    break;
                }
        }
        tokens.add(new Token(Tipo_Token.EOF, "", null, linea));
        return tokens;
    }
    
    private void AgregarToken(String token) {
        tokens.add(new Token(TokensConLexema.get(token),token, null,linea));
        buffer.delete(0, buffer.length());
  }
    private void AgregaToken(String lexema, Tipo_Token Tipo_Token) {
        tokens.add(new Token(Tipo_Token,lexema,null, linea));
        buffer.delete(0, buffer.length());
    }
}
