package org.researchstack.backboneapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.researchstack.backbone.StorageAccess;
import org.researchstack.backbone.answerformat.AnswerFormat;
import org.researchstack.backbone.answerformat.BooleanAnswerFormat;
import org.researchstack.backbone.answerformat.ChoiceAnswerFormat;
import org.researchstack.backbone.answerformat.DateAnswerFormat;
import org.researchstack.backbone.answerformat.IntegerAnswerFormat;
import org.researchstack.backbone.answerformat.TextAnswerFormat;
import org.researchstack.backbone.answerformat.UnknownAnswerFormat;
import org.researchstack.backbone.model.Choice;
import org.researchstack.backbone.model.ConsentDocument;
import org.researchstack.backbone.model.ConsentSection;
import org.researchstack.backbone.model.ConsentSignature;
import org.researchstack.backbone.result.StepResult;
import org.researchstack.backbone.result.TaskResult;
import org.researchstack.backbone.step.ConsentDocumentStep;
import org.researchstack.backbone.step.ConsentSignatureStep;
import org.researchstack.backbone.step.ConsentVisualStep;
import org.researchstack.backbone.step.FormStep;
import org.researchstack.backbone.step.InstructionStep;
import org.researchstack.backbone.step.QuestionStep;
import org.researchstack.backbone.step.Step;
import org.researchstack.backbone.task.OrderedTask;
import org.researchstack.backbone.task.Task;
import org.researchstack.backbone.ui.PinCodeActivity;
import org.researchstack.backbone.ui.ViewTaskActivity;
import org.researchstack.backbone.ui.step.layout.ConsentSignatureStepLayout;
import org.researchstack.backbone.utils.ResUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.cornell.tech.foundry.sdl_rsx.step.RSXSingleImageClassificationSurveyStep;
import edu.cornell.tech.foundry.sdl_rsx.task.YADLFullAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.task.YADLSpotAssessmentTask;
import edu.cornell.tech.foundry.sdl_rsx.utils.ImageDescriptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainActivity extends PinCodeActivity
{

    public static final String PREFS_NAME = "MyPrefsFile";

    private static final String LOG_TAG = "SDL-RSX";

    // Activity Request Codes
    private static final int REQUEST_CONSENT = 0;
    private static final int REQUEST_SURVEY  = 1;
    private static final int REQUEST_YADL_FULL  = 2;
    private static final int REQUEST_YADL_SPOT  = 3;

    // Task/Step Identifiers
    public static final  String FORM_STEP                 = "form_step";
    public static final  String AGE                       = "age";
    public static final  String INSTRUCTION               = "identifier";
    public static final  String BASIC_INFO_HEADER         = "basic_info_header";
    public static final  String FORM_AGE                  = "form_age";
    public static final  String FORM_GENDER               = "gender";
    public static final  String FORM_MULTI_CHOICE         = "multi_choice";
    public static final  String FORM_DATE_OF_BIRTH        = "date_of_birth";
    public static final  String NUTRITION                 = "nutrition";
    public static final  String SIGNATURE                 = "signature";
    public static final  String SIGNATURE_DATE            = "signature_date";
    public static final  String VISUAL_CONSENT_IDENTIFIER = "visual_consent_identifier";
    public static final  String CONSENT_DOC               = "consent_doc";
    public static final  String SIGNATURE_FORM_STEP       = "form_step";
    public static final  String NAME                      = "name";
    public static final  String CONSENT                   = "consent";
    public static final  String MULTI_STEP                = "multi_step";
    public static final  String DATE                      = "date";
    public static final  String DECIMAL                   = "decimal";
    private static final String FORM_NAME                 = "form_name";
    public static final  String SAMPLE_SURVEY             = "sample_survey";
    public static final  String YADL_FULL_ASSESSMENT             = "yadl_full_assessment";
    public static final  String YADL_SPOT_ASSESSMENT             = "yadl_spot_assessment";

    // Views
    private AppCompatButton consentButton;
    private AppCompatButton surveyButton;
    private AppCompatButton yadlFullButton;
    private AppCompatButton yadlSpotButton;
    private AppCompatButton medlFullButton;
    private AppCompatButton medlSpotButton;
    private AppCompatButton pamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOG_TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        consentButton = (AppCompatButton) findViewById(R.id.consent_button);
        consentButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchConsent();
            }
        });

        surveyButton = (AppCompatButton) findViewById(R.id.survey_button);
        surveyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchSurvey();
            }
        });

        yadlFullButton = (AppCompatButton) findViewById(R.id.yadl_full_button);
        yadlFullButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchYADLFull();
            }
        });

        yadlSpotButton = (AppCompatButton) findViewById(R.id.yadl_spot_button);
        yadlSpotButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchYADLSpot();
            }
        });

        medlFullButton = (AppCompatButton) findViewById(R.id.medl_full_button);
        medlFullButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchMEDLFull();
            }
        });

        medlSpotButton = (AppCompatButton) findViewById(R.id.medl_spot_button);
        medlSpotButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchMEDLSpot();
            }
        });

        pamButton = (AppCompatButton) findViewById(R.id.pam_button);
        pamButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchPAM();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.menu_clear)
        {
            clearData();
            Toast.makeText(this, R.string.menu_data_cleared, Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    private void clearData()
    {
        AppPrefs appPrefs = AppPrefs.getInstance(this);
        appPrefs.setHasSurveyed(false);
        appPrefs.setHasConsented(false);

        initViews();
    }

    @Override
    public void onDataReady()
    {
        super.onDataReady();
        initViews();
    }

    private void initViews()
    {
        AppPrefs prefs = AppPrefs.getInstance(this);

        View lblConsentedDate = findViewById(R.id.consented_date_lbl);
        TextView consentedDate = (TextView)findViewById(R.id.consented_date);
        ImageView consentedSig = (ImageView) findViewById(R.id.consented_signature);


        if(prefs.hasConsented())
        {
            consentButton.setVisibility(View.GONE);
            surveyButton.setEnabled(true);

            consentedSig.setVisibility(View.VISIBLE);
            consentedDate.setVisibility(View.VISIBLE);
            lblConsentedDate.setVisibility(View.VISIBLE);

            printConsentInfo(consentedDate, consentedSig);
        }
        else
        {
            consentButton.setVisibility(View.VISIBLE);
            surveyButton.setEnabled(false);

            consentedSig.setVisibility(View.INVISIBLE);
            consentedSig.setImageBitmap(null);
            consentedDate.setVisibility(View.INVISIBLE);
            lblConsentedDate.setVisibility(View.INVISIBLE);
        }

        TextView surveyAnswer = (TextView) findViewById(R.id.survey_results);

        if(prefs.hasSurveyed())
        {
            surveyAnswer.setVisibility(View.VISIBLE);
//            printSurveyInfo(surveyAnswer);
        }
        else
        {
            surveyAnswer.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CONSENT && resultCode == RESULT_OK)
        {
            processConsentResult((TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));
        }
        else if(requestCode == REQUEST_SURVEY && resultCode == RESULT_OK)
        {
            processSurveyResult((TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));
        }
        else if(requestCode == REQUEST_YADL_FULL && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "YADL FULL FINISHED");

            processYADLFullResult((TaskResult) data.getSerializableExtra(ViewTaskActivity.EXTRA_TASK_RESULT));
        }
        else if(requestCode == REQUEST_YADL_SPOT && resultCode == RESULT_OK) {
            Log.i(LOG_TAG, "YADL SPOT FINISHED");
        }
    }

    // Consent stuff

    private void launchConsent()
    {
        ConsentDocument document = new ConsentDocument();
        document.setTitle("Demo Consent");
        document.setSignaturePageTitle(R.string.rsb_consent);

        // Create consent visual sections
        ConsentSection section1 = new ConsentSection(ConsentSection.Type.DataGathering);
        section1.setTitle("The title of the section goes here ...");
        section1.setSummary("The summary about the section goes here ...");
        section1.setContent("The content to show in learn more ...");

        // ...add more sections as needed, then create a visual consent step
        ConsentVisualStep visualStep = new ConsentVisualStep(VISUAL_CONSENT_IDENTIFIER);
        visualStep.setStepTitle(R.string.rsb_consent);
        visualStep.setSection(section1);
        visualStep.setNextButtonString(getString(R.string.rsb_next));

        // Create consent signature object and set what info is required
        ConsentSignature signature = new ConsentSignature();
        signature.setRequiresName(true);
        signature.setRequiresSignatureImage(true);

        // Create our HTML to show the user and have them accept or decline.
        StringBuilder docBuilder = new StringBuilder(
                "</br><div style=\"padding: 10px 10px 10px 10px;\" class='header'>");
        String title = getString(R.string.rsb_consent_review_title);
        docBuilder.append(String.format(
                "<h1 style=\"text-align: center; font-family:sans-serif-light;\">%1$s</h1>",
                title));
        String detail = getString(R.string.rsb_consent_review_instruction);
        docBuilder.append(String.format("<p style=\"text-align: center\">%1$s</p>", detail));
        docBuilder.append("</div></br>");
        docBuilder.append("<div><h2> HTML Consent Doc goes here </h2></div>");

        // Create the Consent doc step, pass in our HTML doc
        ConsentDocumentStep documentStep = new ConsentDocumentStep(CONSENT_DOC);
        documentStep.setConsentHTML(docBuilder.toString());
        documentStep.setConfirmMessage(getString(R.string.rsb_consent_review_reason));

        // Create Consent form step, to get users first & last name
        FormStep formStep = new FormStep(SIGNATURE_FORM_STEP,
                "Form Title",
                "Form step description");
        formStep.setStepTitle(R.string.rsb_consent);

        TextAnswerFormat format = new TextAnswerFormat();
        format.setIsMultipleLines(false);

        QuestionStep fullName = new QuestionStep(NAME, "Full name", format);
        formStep.setFormSteps(Collections.singletonList(fullName));

        // Create Consent signature step, user can sign their name
        ConsentSignatureStep signatureStep = new ConsentSignatureStep(SIGNATURE);
        signatureStep.setStepTitle(R.string.rsb_consent);
        signatureStep.setTitle(getString(R.string.rsb_consent_signature_title));
        signatureStep.setText(getString(R.string.rsb_consent_signature_instruction));
        signatureStep.setSignatureDateFormat(signature.getSignatureDateFormatString());
        signatureStep.setOptional(false);
        signatureStep.setStepLayoutClass(ConsentSignatureStepLayout.class);

        // Finally, create and present a task including these steps.
        Task consentTask = new OrderedTask(CONSENT,
                visualStep,
                documentStep,
                formStep,
                signatureStep);

        // Launch using hte ViewTaskActivity and make sure to listen for the activity result
        Intent intent = ViewTaskActivity.newIntent(this, consentTask);
        startActivityForResult(intent, REQUEST_CONSENT);
    }

    private void processConsentResult(TaskResult result)
    {
        boolean consented = (boolean) result.getStepResult(CONSENT_DOC).getResult();

        if(consented)
        {
            StorageAccess.getInstance().getAppDatabase().saveTaskResult(result);

            AppPrefs prefs = AppPrefs.getInstance(this);
            prefs.setHasConsented(true);

            initViews();
        }
    }

    private void printConsentInfo(TextView consentedDate, ImageView consentedSig)
    {
        TaskResult result = StorageAccess.getInstance()
                .getAppDatabase()
                .loadLatestTaskResult(CONSENT);

        String signatureBase64 = (String) result.getStepResult(SIGNATURE)
                .getResultForIdentifier(ConsentSignatureStepLayout.KEY_SIGNATURE);

        String signatureDate = (String) result.getStepResult(SIGNATURE)
                .getResultForIdentifier(ConsentSignatureStepLayout.KEY_SIGNATURE_DATE);

        consentedDate.setText(signatureDate);

        byte[] signatureBytes = Base64.decode(signatureBase64, Base64.DEFAULT);
        consentedSig.setImageBitmap(BitmapFactory.decodeByteArray(
                signatureBytes,
                0,
                signatureBytes.length));
    }


    // Survey Stuff

    private void launchSurvey()
    {
        InstructionStep instructionStep = new InstructionStep(INSTRUCTION,
                "Selection Survey",
                "This survey can help us understand your eligibility for the fitness study");
        instructionStep.setStepTitle(R.string.survey);

        TextAnswerFormat format = new TextAnswerFormat();
        QuestionStep ageStep = new QuestionStep(NAME, "What is your name?", format);
        ageStep.setStepTitle(R.string.survey);

        DateAnswerFormat dateFormat = new DateAnswerFormat(AnswerFormat.DateAnswerStyle.Date);
        QuestionStep dateStep = new QuestionStep(DATE, "Enter a date", dateFormat);
        dateStep.setStepTitle(R.string.survey);

        // Create a Boolean step to include in the task.
        QuestionStep booleanStep = new QuestionStep(NUTRITION);
        booleanStep.setStepTitle(R.string.survey);
        booleanStep.setTitle("Do you take nutritional supplements?");
        booleanStep.setAnswerFormat(new BooleanAnswerFormat(getString(R.string.rsb_yes),
                getString(R.string.rsb_no)));
        booleanStep.setOptional(false);

        QuestionStep multiStep = new QuestionStep(MULTI_STEP);
        multiStep.setStepTitle(R.string.survey);
        AnswerFormat multiFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice,
                new Choice<>("Zero", 0),
                new Choice<>("One", 1),
                new Choice<>("Two", 2));
        multiStep.setTitle("Select multiple");
        multiStep.setAnswerFormat(multiFormat);
        multiStep.setOptional(false);

        // Create a task wrapping the steps.
        OrderedTask task = new OrderedTask(SAMPLE_SURVEY, instructionStep, ageStep, dateStep,
                // formStep,
                booleanStep, multiStep);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_SURVEY);
    }

    @NonNull
    private FormStep createFormStep()
    {
        FormStep formStep = new FormStep(FORM_STEP, "Form", "Form groups multi-entry in one page");
        ArrayList<QuestionStep> formItems = new ArrayList<>();

        QuestionStep basicInfoHeader = new QuestionStep(BASIC_INFO_HEADER,
                "Basic Information",
                new UnknownAnswerFormat());
        formItems.add(basicInfoHeader);

        TextAnswerFormat format = new TextAnswerFormat();
        format.setIsMultipleLines(false);
        QuestionStep nameItem = new QuestionStep(FORM_NAME, "Name", format);
        formItems.add(nameItem);

        QuestionStep ageItem = new QuestionStep(FORM_AGE, "Age", new IntegerAnswerFormat(18, 90));
        formItems.add(ageItem);

        AnswerFormat genderFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
                new Choice<>("Male", 0),
                new Choice<>("Female", 1));
        QuestionStep genderFormItem = new QuestionStep(FORM_GENDER, "Gender", genderFormat);
        formItems.add(genderFormItem);

        AnswerFormat multiFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.MultipleChoice,
                new Choice<>("Zero", 0),
                new Choice<>("One", 1),
                new Choice<>("Two", 2));
        QuestionStep multiFormItem = new QuestionStep(FORM_MULTI_CHOICE, "Test Multi", multiFormat);
        formItems.add(multiFormItem);

        AnswerFormat dateOfBirthFormat = new DateAnswerFormat(AnswerFormat.DateAnswerStyle.Date);
        QuestionStep dateOfBirthFormItem = new QuestionStep(FORM_DATE_OF_BIRTH,
                "Birthdate",
                dateOfBirthFormat);
        formItems.add(dateOfBirthFormItem);

        // ... And so on, adding additional items
        formStep.setFormSteps(formItems);
        return formStep;
    }

    private void processSurveyResult(TaskResult result)
    {
        StorageAccess.getInstance().getAppDatabase().saveTaskResult(result);
        AppPrefs prefs = AppPrefs.getInstance(this);
        prefs.setHasSurveyed(true);
        initViews();
    }



    private void processYADLFullResult(TaskResult result)
    {
        StorageAccess.getInstance().getAppDatabase().saveTaskResult(result);

        Set<String> activities = new HashSet<String>();
        for(String id : result.getResults().keySet())
        {
            StepResult stepResult = result.getStepResult(id);
            if (stepResult != null && stepResult.getResults() != null && stepResult.getResults().get("answer") != null) {
                String answer = (String) stepResult.getResults().get("answer");
                if (answer.equals("hard") || answer.equals("moderate" )) {
                    activities.add(id);
                }
            }
        }

        AppPrefs prefs = AppPrefs.getInstance(this);
        prefs.setYADLActivities(activities);


//        Log.i(LOG_TAG, result);


    }

//    private void printSurveyInfo(TextView surveyAnswer)
//    {
//        TaskResult taskResult = StorageAccess.getInstance()
//                .getAppDatabase()
//                .loadLatestTaskResult(SAMPLE_SURVEY);
//
//        String results = "";
//        for(String id : taskResult.getResults().keySet())
//        {
//            StepResult stepResult = taskResult.getStepResult(id);
//            results += id + ": " + stepResult.getResult().toString() + "\n";
//        }
//
//        surveyAnswer.setText(results);
//    }


    public class ActivityDescriptor {

        public String identifier;
        public String imageTitle;
        public String description;

        ActivityDescriptor(String identifier, String imageTitle, String description) {
            this.identifier = identifier;
            this.imageTitle = imageTitle;
            this.description = description;
        }

    }

    private ArrayList<ActivityDescriptor> loadActivitiesFromJSONFile(int resourceID) {


        //Get Data From Text Resource File Contains Json Data.
        InputStream inputStream = getResources().openRawResource(resourceID);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("Text Data", byteArrayOutputStream.toString());
        ArrayList<ActivityDescriptor> activities = new ArrayList<>();
        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject completeJSON = new JSONObject(
                    byteArrayOutputStream.toString());
            JSONObject typeJSON = completeJSON.getJSONObject("YADL");
            JSONObject assessmentJSON = typeJSON.getJSONObject("full");
            JSONArray activitiesJSON = typeJSON.getJSONArray("activities");
            String identifier = "";
            String imageTitle = "";
            String description = "";
            for (int i = 0; i < activitiesJSON.length(); i++) {
                identifier = activitiesJSON.getJSONObject(i).getString("identifier");
                imageTitle = activitiesJSON.getJSONObject(i).getString("imageTitle");
                description = activitiesJSON.getJSONObject(i).getString("description");
                activities.add(new ActivityDescriptor(identifier, imageTitle, description));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activities;
    }

    private void launchYADLFull()
    {
        Log.i(LOG_TAG, "Launching YADL Full Assessment");

//        ArrayList<ActivityDescriptor> activities = this.loadActivitiesFromJSONFile(R.raw.yadl);
//
//        String prompt = "How hard is this activity for you on a difficult day?";
//
//        AnswerFormat answerFormat = new ChoiceAnswerFormat(AnswerFormat.ChoiceAnswerStyle.SingleChoice,
//                new Choice<>("Easy", "Easy"),
//                new Choice<>("Moderate", "Moderate"),
//                new Choice<>("Hard", "Hard"));
//
//
//        ArrayList<Step> yadlFullSteps = new ArrayList<>();
//
//        for(int i=0; i<activities.size(); i++) {
//
//            ActivityDescriptor activityDescriptor = activities.get(i);
//
////            int resId = ResUtils.getDrawableResourceId(this, activityDescriptor.imageTitle);
////            Bitmap image = BitmapFactory.decodeResource(getResources(), resId);
//
////            String image = edu.cornell.tech.foundry.sdl_rsx.utils.ImageUtils.encodeToBase64(bm, Bitmap.CompressFormat.JPEG, 100);
//
//            RSXSingleImageClassificationSurveyStep yadlFullStep = new RSXSingleImageClassificationSurveyStep(
//                    activityDescriptor.identifier,
//                    activityDescriptor.description,
//                    prompt,
//                    activityDescriptor.imageTitle,
//                    answerFormat
//            );
//
//            yadlFullSteps.add(yadlFullStep);
//        }
//        // Create a task wrapping the steps.
////        OrderedTask task = new OrderedTask(YADL_FULL_ASSESSMENT, yadlFullSteps);

        OrderedTask task = YADLFullAssessmentTask.create(YADL_FULL_ASSESSMENT, "yadl", this);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_YADL_FULL);

    }

    private void launchYADLSpot()
    {
        Log.i(LOG_TAG, "Launching YADL Spot Assessment");

        AppPrefs prefs = AppPrefs.getInstance(this);

        Set<String> selectedActivies = prefs.getYADLActivities();

        OrderedTask task = YADLSpotAssessmentTask.create(YADL_SPOT_ASSESSMENT, "yadl", this, selectedActivies);

        // Create an activity using the task and set a delegate.
        Intent intent = ViewTaskActivity.newIntent(this, task);
        startActivityForResult(intent, REQUEST_YADL_SPOT);

    }

    private void launchMEDLFull()
    {
        Log.i(LOG_TAG, "Launching MEDL Full Assessment");

    }

    private void launchMEDLSpot() {
        Log.i(LOG_TAG, "Launching MEDL Spot Assessment");

    }

    private void launchPAM()
    {
        Log.i(LOG_TAG, "Launching PAM");

    }
}
