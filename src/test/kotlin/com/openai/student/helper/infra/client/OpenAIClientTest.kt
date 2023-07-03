//
//
//package com.openai.student.helper.infra.client
//
//import com.aallam.openai.api.BetaOpenAI
//import com.aallam.openai.api.chat.ChatChoice
//import com.aallam.openai.api.chat.ChatCompletion
//import com.aallam.openai.api.chat.ChatMessage
//import com.aallam.openai.api.model.ModelId
//import com.aallam.openai.client.OpenAI
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.MockitoAnnotations
//
//class OpenAIClientTest {
//
//    private val context = "Você é um auxiliar para estudos."
//    private val question = "Me sugira três pontos chave para estudar sobre: \"Math\""
//
//    @Mock
//    private lateinit var openAiService: OpenAI
//
//    private lateinit var openAIClient: IOpenAIService
//
//    @BeforeEach
//    fun setup() {
//        MockitoAnnotations.openMocks(this)
//        openAIClient = IOpenAIService(openAiKey = "YOUR_OPENAI_KEY")
//        openAiService.also { (openAIClient as IOpenAIService).openAIService = it }
//    }
//
//    @BetaOpenAI
//    @Test
//    fun testIntegrateChatGpt() {
//        val expectedResponse = "Math is an important subject that includes various concepts such as algebra, geometry, and calculus."
//
//        runBlocking {
//            `when`(openAiService.chatCompletion(any()))
//                .thenReturn(createChatCompletionMockResponse(expectedResponse))
//
//            val result = openAIClient.integrateChatGpt(context, question)
//
//            assertEquals(expectedResponse, result)
//            verify(openAiService).chatCompletion(any())
//        }
//    }
//
////    @Test
////    fun testIntegrateChatGpt_InvalidOpenAiKey() {
////        val context = "Você é um auxiliar para estudos."
////        val question = "Me sugira três pontos chave para estudar sobre: \"Math\""
////
////        val chatCompletionRequest = ChatCompletionRequest(
////            model = ModelId(openAIClient.modelId),
////            messages = listOf(
////                ChatMessage(role = ChatRole.System, content = context),
////                ChatMessage(role = ChatRole.User, content = question)
////            )
////        )
////
////        runBlocking {
////            `when`(openAiService.chatCompletion(chatCompletionRequest)).thenThrow(AuthenticationException())
////
////            try {
////                openAIClient.integrateChatGpt(context, question)
////            } catch (e: UnauthorizedException) {
////                assertEquals("User Unauthorized - Invalid Open AI key", e.message)
////            }
////
////            verify(openAiService).chatCompletion(chatCompletionRequest)
////        }
////    }
////
////    @Test
////    fun testIntegrateChatGpt_NoMessage() {
////        val context = "Você é um auxiliar para estudos."
////        val question = "Me sugira três pontos chave para estudar sobre: \"Math\""
////
////        val chatCompletionRequest = ChatCompletionRequest(
////            model = ModelId(openAIClient.modelId),
////            messages = listOf(
////                ChatMessage(role = ChatRole.System, content = context),
////                ChatMessage(role = ChatRole.User, content = question)
////            )
////        )
////
////        runBlocking {
////            `when`(openAiService.chatCompletion(chatCompletionRequest)).thenReturn(createMockResponse(null))
////
////            try {
////                openAIClient.integrateChatGpt(context, question)
////            } catch (e: ResponseStatusException) {
////                assertEquals(NOT_FOUND, e.status)
////                assertEquals("No message", e.reason)
////            }
////
////            verify(openAiService).chatCompletion(chatCompletionRequest)
////        }
////    }
////
//
//    @OptIn(BetaOpenAI::class)
//    private fun createChatCompletionMockResponse(content: String?): ChatCompletion {
//        val chatCompletion: ChatCompletion = mock(ChatCompletion::class.java)
//        val chatChoice = mock(ChatChoice::class.java)
//        val chatMessage = mock(ChatMessage::class.java)
//
//        `when`(chatMessage.content).thenReturn(content)
//
//        `when`(chatChoice.index).thenReturn(0)
//        `when`(chatChoice.finishReason).thenReturn(null)
//        `when`(chatChoice.message).thenReturn(chatMessage)
//
//        `when`(chatCompletion.id).thenReturn("cmpl-3Z4jzN9Z5j5Y5")
//        `when`(chatCompletion.created).thenReturn(1623933304)
//        `when`(chatCompletion.model).thenReturn(ModelId("gpt-3.5-turbo"))
//        `when`(chatCompletion.choices).thenReturn(listOf(chatChoice))
//
//        return chatCompletion
//    }
//}
